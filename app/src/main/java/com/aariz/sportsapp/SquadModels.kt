package com.aariz.sportsapp.models

data class SquadInfoResponse(
    val apikey: String?,
    val data: SquadInfo?,
    val status: String?,
    val message: String?
)

data class SquadInfo(
    val id: String?,
    val name: String?,
    val matchType: String?,
    val status: String?,
    val venue: String?,
    val date: String?,
    val dateTimeGMT: String?,
    val teams: List<String>?,
    val teamInfo: List<TeamInfo>?,
    val players: List<PlayerInfo>?
)

data class PlayerInfo(
    val id: String?,
    val name: String?,
    val country: String?,
    val playerImg: String?
)