package com.aariz.sportsapp.models

data class CurrentMatchesResponse(
    val status: String?,
    val data: List<CurrentMatch>?
)

data class CurrentMatch(
    val name: String?,
    val date: String?,
    val venue: String?,
    val status: String?
)
