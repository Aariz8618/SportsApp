package com.aariz.sportsapp.models

data class PlayerResponse(
    val status: String,
    val data: List<Player>
)

data class Player(
    val id: String,
    val name: String,
    val country: String,
    var playerImg: String? = null
)
