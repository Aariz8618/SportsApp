package com.aariz.sportsapp.api

import com.aariz.sportsapp.models.*
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

    @GET("match_info")
    fun getMatchInfo(
        @Query("apikey") apiKey: String,
        @Query("id") matchId: String
    ): Call<MatchInfoResponse>

    @GET("players_info")
    fun getPlayerInfo(
        @Query("apikey") apiKey: String,
        @Query("id") playerId: String
    ): Call<PlayerDetailResponse>

    @GET("match_squad")
    fun getSquadInfo(
        @Query("apikey") apiKey: String,
        @Query("id") matchId: String
    ): Call<SquadInfoResponse>

    // Series Squad endpoint (uses series_id)
    @GET("series_squad")
    fun getSeriesSquad(
        @Query("apikey") apiKey: String,
        @Query("series_id") seriesId: String
    ): Call<SquadInfoResponse>

    // Scorecard endpoint (uses match id)
    @GET("scorecard")
    fun getScorecard(
        @Query("apikey") apiKey: String,
        @Query("id") matchId: String
    ): Call<ScorecardResponse>
}