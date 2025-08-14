package com.aariz.sportsapp.models

data class MatchInfoResponse(
    val apikey: String?,
    val data: MatchInfo?,
    val status: String?,
    val message: String?
)

data class MatchInfo(
    val id: String?,
    val name: String?, // Match name like "India vs England"
    val status: String?, // e.g., "Live", "Completed"
    val result: String?, // Final result if completed
    val date: String?, // Match date
    val dateTimeGMT: String?, // Match date in GMT
    val matchType: String?, // e.g., "ODI", "T20"
    val venue: String?,
    val tossWinner: String?,
    val tossChoice: String?,
    val matchWinner: String?,
    val winBy: String ?,
    val series: String?, // Series name
    val seriesId: String?,
    val teams: List<String>?, // Team names list
    val teamInfo: List<TeamInfo>?, // Detailed team info
    val score: List<ScoreInfo>?, // Multiple innings possible
    val players: List<PlayerShortInfo>?,
    val officials: OfficialsInfo?
)

data class TeamInfo(
    val name: String?,
    val shortName: String?,
    val img: String?
)

data class PlayerShortInfo(
    val id: String?,
    val name: String?,
    val role: String?,
    val battingStyle: String?,
    val bowlingStyle: String?
)

data class ScoreInfo(
    val inning: String?,
    val r: Int?, // Runs
    val w: Int?, // Wickets
    val o: Double?  // Overs
)

data class OfficialsInfo(
    val referee: String?,
    val umpire1: String?,
    val umpire2: String?,
    val umpire3: String?
)