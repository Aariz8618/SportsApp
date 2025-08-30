package com.aariz.sportsapp.models

data class MatchItem(
    val name: String?,
    val date: String?,
    val venue: String?,
    val status: String?,
    val id: String?,
    val matchType: String? = null,
    val matchCount: String? = null,
    val result: String? = null
)