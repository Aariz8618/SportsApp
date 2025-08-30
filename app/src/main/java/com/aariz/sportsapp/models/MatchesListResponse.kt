package com.aariz.sportsapp.models

data class MatchesListResponse(
    val status: String?,
    val data: List<UpcomingMatch>?
)

data class UpcomingMatch(
    val name: String?,
    val date: String?,
    val venue: String?,
    val status: String?,
    val type: String?,
    val id: String? = null  // Added ID field for match details
)