package com.aariz.sportsapp.models

data class MatchItem(
    val name: String,
    val date: String?,
    val venue: String?,
    val status: String?,
    val id: String? = null,
    val result: String? = null,
    val scores: List<ScoreData>? = null
)