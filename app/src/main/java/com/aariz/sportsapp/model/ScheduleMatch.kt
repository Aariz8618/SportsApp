package com.aariz.sportsapp.model

import java.text.SimpleDateFormat
import java.util.*

data class ScheduleMatch(
    val matchId: Int,
    val seriesName: String,
    val matchDesc: String,
    val matchFormat: String,
    val startDate: String,
    val endDate: String,
    val team1Name: String,
    val team1ShortName: String,
    val team2Name: String,
    val team2ShortName: String,
    val venue: String,
    val city: String,
    val country: String,
    val timezone: String,
    val dateFormatted: String
) {
    companion object {
        fun fromMatchInfo(matchInfo: MatchInfo, seriesName: String, dateFormatted: String): ScheduleMatch {
            return ScheduleMatch(
                matchId = matchInfo.matchId,
                seriesName = seriesName,
                matchDesc = matchInfo.matchDesc,
                matchFormat = matchInfo.matchFormat,
                startDate = matchInfo.startDate,
                endDate = matchInfo.endDate,
                team1Name = matchInfo.team1.teamName,
                team1ShortName = matchInfo.team1.teamSName,
                team2Name = matchInfo.team2.teamName,
                team2ShortName = matchInfo.team2.teamSName,
                venue = matchInfo.venueInfo.ground,
                city = matchInfo.venueInfo.city,
                country = matchInfo.venueInfo.country,
                timezone = matchInfo.venueInfo.timezone,
                dateFormatted = dateFormatted
            )
        }
        
        fun convertTimestampToDate(timestamp: String): String {
            return try {
                val date = Date(timestamp.toLong())
                val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                formatter.format(date)
            } catch (e: Exception) {
                "Date unavailable"
            }
        }
    }
}
