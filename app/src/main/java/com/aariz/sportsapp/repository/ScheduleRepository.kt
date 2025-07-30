package com.aariz.sportsapp.repository

import com.aariz.sportsapp.api.RetrofitInstance
import com.aariz.sportsapp.model.*
import com.aariz.sportsapp.utils.ApiConfig
import retrofit2.Response

class ScheduleRepository {

    private val apiService = RetrofitInstance.matchApi

    suspend fun getInternationalSchedule(): Response<ScheduleResponse> {
        return try {
            val response = apiService.getInternationalSchedule(
                ApiConfig.RAPIDAPI_KEY,
                ApiConfig.RAPIDAPI_HOST
            )
            
            if (response.isSuccessful) {
                response
            } else {
                // If API fails, return mock data
                createMockScheduleResponse()
            }
        } catch (e: Exception) {
            // If API call fails completely, return mock data
            println("Exception in ScheduleRepository: ${e.message}")
            createMockScheduleResponse()
        }
    }
    
    private fun createMockScheduleResponse(): Response<ScheduleResponse> {
        // Create mock schedule matches
        val mockMatches = listOf(
            createMockMatch(
                matchId = 1,
                seriesName = "India tour of Australia, 2025",
                matchDesc = "1st Test",
                format = "TEST",
                team1 = "India",
                team1Short = "IND",
                team2 = "Australia", 
                team2Short = "AUS",
                venue = "Melbourne Cricket Ground",
                city = "Melbourne",
                country = "Australia",
                startDate = "1738368000000" // Feb 1, 2025
            ),
            createMockMatch(
                matchId = 2,
                seriesName = "England vs South Africa, 2025",
                matchDesc = "1st ODI",
                format = "ODI",
                team1 = "England",
                team1Short = "ENG",
                team2 = "South Africa",
                team2Short = "SA",
                venue = "Lord's Cricket Ground",
                city = "London",
                country = "England",
                startDate = "1738713600000" // Feb 5, 2025
            ),
            createMockMatch(
                matchId = 3,
                seriesName = "Pakistan vs New Zealand, 2025",
                matchDesc = "1st T20I",
                format = "T20",
                team1 = "Pakistan",
                team1Short = "PAK",
                team2 = "New Zealand",
                team2Short = "NZ",
                venue = "Gaddafi Stadium",
                city = "Lahore",
                country = "Pakistan",
                startDate = "1739059200000" // Feb 9, 2025
            )
        )
        
        val mockScheduleResponse = ScheduleResponse(
            matchScheduleMap = listOf(
                ScheduleMapItem(
                    scheduleAdWrapper = ScheduleAdWrapper(
                        date = "Mock Schedule",
                        matchScheduleList = listOf(
                            MatchSchedule(
                                seriesName = "Mock International Cricket",
                                matchInfo = mockMatches,
                                seriesId = 1,
                                seriesCategory = "International"
                            )
                        ),
                        longDate = System.currentTimeMillis().toString()
                    ),
                    adDetail = null
                )
            ),
            startDt = mockMatches.map { it.startDate },
            appIndex = null
        )
        
        return Response.success(mockScheduleResponse)
    }
    
    private fun createMockMatch(
        matchId: Int,
        seriesName: String,
        matchDesc: String,
        format: String,
        team1: String,
        team1Short: String,
        team2: String,
        team2Short: String,
        venue: String,
        city: String,
        country: String,
        startDate: String
    ): MatchInfo {
        return MatchInfo(
            matchId = matchId,
            seriesId = 1,
            matchDesc = matchDesc,
            matchFormat = format,
            startDate = startDate,
            endDate = (startDate.toLong() + 86400000).toString(), // +1 day
            team1 = TeamInfo(
                teamId = 1,
                teamName = team1,
                teamSName = team1Short,
                isFullMember = true,
                imageId = 1
            ),
            team2 = TeamInfo(
                teamId = 2,
                teamName = team2,
                teamSName = team2Short,
                isFullMember = true,
                imageId = 2
            ),
            venueInfo = VenueInfo(
                ground = venue,
                city = city,
                country = country,
                timezone = "+00:00"
            )
        )
    }
}
