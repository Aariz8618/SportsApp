package com.aariz.sportsapp.models

import com.google.gson.annotations.SerializedName

data class CurrentMatchesResponse(
    val status: String?,
    val data: List<MatchData>?,
    val info: InfoData? // Sometimes APIs include meta info like credits/total
)

data class MatchData(
    val id: String?,
    val name: String?,           // Match name (e.g., "IND vs ENG")
    val status: String?,         // Live/Completed/Scheduled
    val venue: String?,
    val date: String?,           // Match date
    val dateTimeGMT: String?,    // GMT format date-time
    val matchType: String?,      // ODI, T20, Test
    val teams: List<String>?,    // Team names (older variant)
    val score: List<ScoreData>?, // List of innings scores
    val series_id: String?,      // Series identifier
    val fantasyEnabled: Boolean?,
    val bbbEnabled: Boolean?,
    // Newer/alternate fields from API samples
    val ms: String?,                    // match state (e.g., fixture)
    val t1: String?,                    // Team 1 name (e.g., "Barbados Royals [BR]")
    val t2: String?,                    // Team 2 name
    val t1s: String?,                   // Team 1 score string
    val t2s: String?,                   // Team 2 score string
    val t1img: String?,                 // Team 1 image URL
    val t2img: String?,                 // Team 2 image URL
    @SerializedName("series") val seriesName: String? // e.g., "Caribbean Premier League 2025"
)

data class ScoreData(
    val r: Int?,     // Runs
    val w: Int?,     // Wickets
    val o: Double?,  // Overs
    val inning: String? // e.g., "India Inning 1"
)

data class InfoData(
    val hitsToday: Int?,
    val hitsUsed: Int?,
    val hitsLimit: Int?,
    val credits: Int?,
    val server: String?,
    val offsetRows: Int?,
    val totalRows: Int?,
    val queryTime: Double?,
    val s: Int?
)