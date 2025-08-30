package com.aariz.sportsapp.data.model

data class MatchItem(
    val name: String,
    val date: String?,
    val venue: String?,
    val status: String?,
    val id: String? = null,
    val result: String? = null,
    val matchType: String = "T20",  // Default to T20 if not specified
    val matchCount: String = ""      // Will hold match number like "1", "2", etc.
)
