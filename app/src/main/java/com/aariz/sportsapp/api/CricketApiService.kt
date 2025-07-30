package com.aariz.sportsapp.api

import com.aariz.sportsapp.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface CricketApiService {
    
    // Schedule
    @GET("schedule/v1/international")
    suspend fun getInternationalSchedule(
        @Header("x-rapidapi-key") apiKey: String,
        @Header("x-rapidapi-host") host: String
    ): Response<ScheduleResponse>
    
    // Players
    @GET("stats/v1/player/trending")
    suspend fun getTrendingPlayers(
        @Header("x-rapidapi-key") apiKey: String,
        @Header("x-rapidapi-host") host: String
    ): Response<TrendingPlayerResponse>
    
    @GET("stats/v1/player/search")
    suspend fun searchPlayers(
        @Header("x-rapidapi-key") apiKey: String,
        @Header("x-rapidapi-host") host: String,
        @Query("plrN") playerName: String
    ): Response<TrendingPlayerResponse>
}
