package com.aariz.sportsapp.models

data class PlayerDetailResponse(
    val status: String,
    val data: PlayerDetail
)

data class PlayerDetail(
    val id: String,
    val name: String,
    val dateOfBirth: String?,
    val role: String?,
    val battingStyle: String?,
    val bowlingStyle: String?,
    val placeOfBirth: String?,
    val country: String?,
    val playerImg: String?,
    val stats: PlayerStats? // Optional, if API supports
)

data class PlayerStats(
    val matches: Int?,
    val runs: Int?,
    val wickets: Int?,
    val average: Double?
)
