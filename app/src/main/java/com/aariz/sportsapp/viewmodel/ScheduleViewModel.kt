package com.aariz.sportsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aariz.sportsapp.model.ScheduleMatch
import com.aariz.sportsapp.repository.ScheduleRepository
import kotlinx.coroutines.launch

class ScheduleViewModel(private val repository: ScheduleRepository) : ViewModel() {

    private val _scheduleMatches = MutableLiveData<List<ScheduleMatch>>()
    val scheduleMatches: LiveData<List<ScheduleMatch>> = _scheduleMatches

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchSchedule() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                println("Making API call to Cricbuzz Schedule API")
                val response = repository.getInternationalSchedule()
                println("API response code: ${response.code()}")
                println("API response message: ${response.message()}")

                if (response.isSuccessful && response.body() != null) {
                    val scheduleResponse = response.body()!!
                    val matches = mutableListOf<ScheduleMatch>()

                    // Parse the complex schedule response into flat list of matches
                    for (mapItem in scheduleResponse.matchScheduleMap) {
                        mapItem.scheduleAdWrapper?.let { wrapper ->
                            for (matchSchedule in wrapper.matchScheduleList) {
                                for (matchInfo in matchSchedule.matchInfo) {
                                    val scheduleMatch = ScheduleMatch.fromMatchInfo(
                                        matchInfo = matchInfo,
                                        seriesName = matchSchedule.seriesName,
                                        dateFormatted = ScheduleMatch.convertTimestampToDate(matchInfo.startDate)
                                    )
                                    matches.add(scheduleMatch)
                                }
                            }
                        }
                    }

                    // Sort matches by start date
                    val sortedMatches = matches.sortedBy { it.startDate.toLongOrNull() ?: 0L }
                    println("Parsed ${sortedMatches.size} scheduled matches")
                    _scheduleMatches.value = sortedMatches
                    _error.value = null
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("API error body: $errorBody")
                    val errorMessage = when (response.code()) {
                        400 -> "Bad Request: Check API parameters and token validity"
                        401 -> "Unauthorized: Invalid API token"
                        403 -> "Forbidden: API access denied"
                        404 -> "Not Found: API endpoint not found"
                        500 -> "Server Error: API server is experiencing issues"
                        else -> "HTTP ${response.code()}: ${response.message()}"
                    }
                    _error.value = "Failed to fetch schedule: $errorMessage"
                }
            } catch (e: Exception) {
                println("Exception occurred: ${e.message}")
                e.printStackTrace()
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
