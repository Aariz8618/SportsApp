package com.aariz.sportsapp.repository

import com.aariz.sportsapp.api.RetrofitInstance
import com.aariz.sportsapp.utils.ApiConfig
import com.aariz.sportsapp.model.*
import retrofit2.Response
import okhttp3.ResponseBody

class PlayerRepository {
    
    private val apiService = RetrofitInstance.playerApi
    
    suspend fun getPlayers(): Response<PlayerResponse> {
        return try {
            // Try to get trending players from API first
            val trendingResponse = apiService.getTrendingPlayers(
                ApiConfig.RAPIDAPI_KEY,
                ApiConfig.RAPIDAPI_HOST
            )
            
            if (trendingResponse.isSuccessful && trendingResponse.body() != null) {
                // Convert trending players to regular Player objects
                val trendingPlayers = trendingResponse.body()!!.player
                val convertedPlayers = trendingPlayers.map { convertTrendingPlayerToPlayer(it) }
                val playerResponse = PlayerResponse(data = convertedPlayers, meta = null)
                Response.success(playerResponse)
            } else {
                // If API fails, return mock data
                createMockPlayerResponse()
            }
        } catch (e: Exception) {
            // If API call fails completely, return mock data
            println("Exception fetching trending players: ${e.message}")
            createMockPlayerResponse()
        }
    }
    
    suspend fun searchPlayers(playerName: String): Response<PlayerResponse> {
        return try {
            if (playerName.isBlank()) {
                // If search is empty, return trending players
                return getPlayers()
            }
            
            // Search for players using the search API
            val searchResponse = apiService.searchPlayers(
                ApiConfig.RAPIDAPI_KEY,
                ApiConfig.RAPIDAPI_HOST,
                playerName
            )
            
            if (searchResponse.isSuccessful && searchResponse.body() != null) {
                // Convert search results to regular Player objects
                val searchResults = searchResponse.body()!!.player
                val convertedPlayers = searchResults.map { convertTrendingPlayerToPlayer(it) }
                val playerResponse = PlayerResponse(data = convertedPlayers, meta = null)
                Response.success(playerResponse)
            } else {
                // If search fails, return empty list
                val emptyResponse = PlayerResponse(data = emptyList(), meta = null)
                Response.success(emptyResponse)
            }
        } catch (e: Exception) {
            println("Exception searching players: ${e.message}")
            // If search fails, return empty list
            val emptyResponse = PlayerResponse(data = emptyList(), meta = null)
            Response.success(emptyResponse)
        }
    }
    
    
    private fun convertTrendingPlayerToPlayer(trendingPlayer: TrendingPlayer): Player {
        // Extract first and last name from full name
        val nameParts = trendingPlayer.name.split(" ", limit = 2)
        val firstName = nameParts.getOrNull(0) ?: ""
        val lastName = nameParts.getOrNull(1) ?: ""
        
        // Create image URL from faceImageId
        val imageUrl = if (trendingPlayer.faceImageId != 0) {
            "https://www.cricbuzz.com/a/img/v1/150x150/i1/c${trendingPlayer.faceImageId}/i.jpg"
        } else null
        
        return Player(
            id = trendingPlayer.id.toIntOrNull() ?: 0,
            firstName = firstName,
            lastName = lastName,
            fullName = trendingPlayer.name,
            imagePath = imageUrl,
            dateOfBirth = null,
            gender = "Male", // Default since API doesn't provide this
            battingStyle = null,
            bowlingStyle = null,
            country = Country(
                id = trendingPlayer.teamName.hashCode(),
                name = trendingPlayer.teamName,
                imagePath = null
            ),
            position = Position(id = 1, name = "Player"), // Default position
            career = null
        )
    }
    
    private fun createMockPlayerResponse(): Response<PlayerResponse> {
        val mockPlayers = listOf(
            Player(
                id = 1,
                firstName = "Virat",
                lastName = "Kohli",
                fullName = "Virat Kohli",
                imagePath = null,
                dateOfBirth = "1988-11-05",
                gender = "Male",
                battingStyle = "Right-handed",
                bowlingStyle = "Right-arm medium",
                country = Country(id = 1, name = "India", imagePath = null),
                position = Position(id = 1, name = "Batsman"),
                career = null
            ),
            Player(
                id = 2,
                firstName = "MS",
                lastName = "Dhoni",
                fullName = "MS Dhoni",
                imagePath = null,
                dateOfBirth = "1981-07-07",
                gender = "Male",
                battingStyle = "Right-handed",
                bowlingStyle = "Right-arm medium",
                country = Country(id = 1, name = "India", imagePath = null),
                position = Position(id = 2, name = "Wicket-keeper"),
                career = null
            ),
            Player(
                id = 3,
                firstName = "Rohit",
                lastName = "Sharma",
                fullName = "Rohit Sharma",
                imagePath = null,
                dateOfBirth = "1987-04-30",
                gender = "Male",
                battingStyle = "Right-handed",
                bowlingStyle = "Right-arm off-break",
                country = Country(id = 1, name = "India", imagePath = null),
                position = Position(id = 1, name = "Batsman"),
                career = null
            ),
            Player(
                id = 4,
                firstName = "Jasprit",
                lastName = "Bumrah",
                fullName = "Jasprit Bumrah",
                imagePath = null,
                dateOfBirth = "1993-12-06",
                gender = "Male",
                battingStyle = "Right-handed",
                bowlingStyle = "Right-arm fast",
                country = Country(id = 1, name = "India", imagePath = null),
                position = Position(id = 3, name = "Bowler"),
                career = null
            ),
            Player(
                id = 5,
                firstName = "Steve",
                lastName = "Smith",
                fullName = "Steve Smith",
                imagePath = null,
                dateOfBirth = "1989-06-02",
                gender = "Male",
                battingStyle = "Right-handed",
                bowlingStyle = "Right-arm leg-break",
                country = Country(id = 2, name = "Australia", imagePath = null),
                position = Position(id = 1, name = "Batsman"),
                career = null
            ),
            Player(
                id = 6,
                firstName = "Ben",
                lastName = "Stokes",
                fullName = "Ben Stokes",
                imagePath = null,
                dateOfBirth = "1991-06-04",
                gender = "Male",
                battingStyle = "Left-handed",
                bowlingStyle = "Right-arm fast-medium",
                country = Country(id = 3, name = "England", imagePath = null),
                position = Position(id = 4, name = "All-rounder"),
                career = null
            ),
            Player(
                id = 7,
                firstName = "Kane",
                lastName = "Williamson",
                fullName = "Kane Williamson",
                imagePath = null,
                dateOfBirth = "1990-08-08",
                gender = "Male",
                battingStyle = "Right-handed",
                bowlingStyle = "Right-arm off-break",
                country = Country(id = 4, name = "New Zealand", imagePath = null),
                position = Position(id = 1, name = "Batsman"),
                career = null
            )
        )
        
        val mockResponse = PlayerResponse(data = mockPlayers, meta = null)
        return Response.success(mockResponse)
    }
}
