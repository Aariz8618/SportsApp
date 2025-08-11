package com.aariz.sportsapp.api

import com.aariz.sportsapp.models.PlayerResponse
import com.aariz.sportsapp.models.PlayerDetailResponse
import com.aariz.sportsapp.models.CurrentMatchesResponse
import com.aariz.sportsapp.models.MatchesListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CricApiService {

    @GET("players")
    fun getPlayers(
        @Query("apikey") apiKey: String,
        @Query("offset") offset: Int,
        @Query("search") searchQuery: String? = null
    ): Call<PlayerResponse>

    @GET("currentMatches")
    fun getCurrentMatches(
        @Query("apikey") apiKey: String,
        @Query("offset") offset: Int
    ): Call<CurrentMatchesResponse>

    @GET("matches")
    fun getMatchesList(
        @Query("apikey") apiKey: String,
        @Query("offset") offset: Int
    ): Call<MatchesListResponse>

    // Player Info with image & stats
    @GET("players_info")
    fun getPlayerInfo(
        @Query("apikey") apiKey: String,
        @Query("id") playerId: String
    ): Call<PlayerDetailResponse>
}
