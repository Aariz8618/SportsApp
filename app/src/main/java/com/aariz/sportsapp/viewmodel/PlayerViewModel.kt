package com.aariz.sportsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aariz.sportsapp.model.Player
import com.aariz.sportsapp.repository.PlayerRepository
import kotlinx.coroutines.launch

class PlayerViewModel(private val repository: PlayerRepository) : ViewModel() {
    
    private val _players = MutableLiveData<List<Player>>(emptyList())
    val players: LiveData<List<Player>> = _players
    
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    private val _currentPage = MutableLiveData<Int>(0)
    val currentPage: LiveData<Int> = _currentPage
    
    private val _totalPages = MutableLiveData<Int>(0)
    val totalPages: LiveData<Int> = _totalPages
    
    private val _isLoadingMore = MutableLiveData<Boolean>(false)
    val isLoadingMore: LiveData<Boolean> = _isLoadingMore
    
    private var hasMorePages = true
    private val allPlayers = mutableListOf<Player>()
    private var retryCount = 0
    private val maxRetries = 3
    
    private val _filteredPlayers = MutableLiveData<List<Player>>(emptyList())
    val filteredPlayers: LiveData<List<Player>> = _filteredPlayers
    
    private var currentSearchQuery = ""
    
    fun fetchPlayers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                println("Making API call to Cricbuzz Trending Players API")
                val response = repository.getPlayers()
                println("Players API response code: ${response.code()}")
                println("Players API response message: ${response.message()}")
                
                if (response.isSuccessful && response.body() != null) {
                    val playerResponse = response.body()!!
                    val newPlayers = playerResponse.data
                    println("Received ${newPlayers.size} players")
                    
                    allPlayers.addAll(newPlayers)
                    _players.value = allPlayers.toList()
                    _error.value = null
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Players API error body: $errorBody")
                    val errorMessage = when (response.code()) {
                        400 -> "Bad Request: Check API parameters and token validity"
                        401 -> "Unauthorized: Invalid API token"
                        403 -> "Forbidden: API access denied"
                        404 -> "Not Found: API endpoint not found"
                        500 -> "Server Error: API server is experiencing issues"
                        else -> "HTTP ${response.code()}: ${response.message()}"
                    }
                    _error.value = "Failed to fetch players: $errorMessage"
                }
            } catch (e: Exception) {
                println("Exception occurred while fetching players: ${e.message}")
                e.printStackTrace()
                _error.value = "Network error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun loadMorePlayers() {
        // No pagination support in the new API
    }
    
    fun refreshPlayers() {
        allPlayers.clear()
        fetchPlayers()
    }
    
    fun searchPlayers(query: String) {
        currentSearchQuery = query.trim()
        
        if (currentSearchQuery.isEmpty()) {
            // If search is empty, show all players
            _filteredPlayers.value = _players.value
        } else {
            // Perform API search
            searchPlayersFromAPI(currentSearchQuery)
        }
    }
    
    private fun searchPlayersFromAPI(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                println("Searching players with query: $query")
                val response = repository.searchPlayers(query)
                
                if (response.isSuccessful && response.body() != null) {
                    val searchResults = response.body()!!.data
                    println("Found ${searchResults.size} players matching '$query'")
                    _filteredPlayers.value = searchResults
                    _error.value = null
                    
                    // Also update the main players list if it's a search result
                    if (searchResults.isNotEmpty()) {
                        _players.value = searchResults
                    }
                } else {
                    println("Search failed: ${response.code()} - ${response.message()}")
                    _filteredPlayers.value = emptyList()
                    _error.value = "No players found matching '$query'"
                }
            } catch (e: Exception) {
                println("Exception during search: ${e.message}")
                e.printStackTrace()
                _filteredPlayers.value = emptyList()
                _error.value = "Search failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearSearch() {
        currentSearchQuery = ""
        _filteredPlayers.value = _players.value
    }
}
