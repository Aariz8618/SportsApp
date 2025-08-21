package com.aariz.sportsapp.models

import com.google.gson.annotations.SerializedName

// Conservative scorecard models with nullable fields to avoid parse failures across API variants

data class ScorecardResponse(
    val apikey: String?,
    val data: ScorecardData?,
    val status: String?,
    val message: String?
)

data class ScorecardData(
    val teams: List<String>?,
    val teamInfo: List<ScoreTeamInfo>?,
    val matchType: String?,
    val status: String?,
    val result: String?,
    val venue: String?,
    val dateTimeGMT: String?,
    val innings: List<Inning>?
)

// Use a distinct name to avoid clashing with MatchInfoResponse.TeamInfo
data class ScoreTeamInfo(
    val name: String?,
    @SerializedName("shortname") val shortName: String?,
    val img: String?
)

data class Inning(
    val inning: String?, // e.g., "IND Innings"
    val r: Int?, // Total runs
    val w: Int?, // Total wickets
    val o: Double?, // Overs
    val target: Int?,
    val rrr: Double?,
    @SerializedName("batsmen") val batsmen: List<Batsman>?,
    @SerializedName("bowlers") val bowlers: List<Bowler>?
)

data class Batsman(
    val name: String?,
    val dismissal: String?,
    val r: Int?,
    val b: Int?,
    @SerializedName("4s") val fours: Int?,
    @SerializedName("6s") val sixes: Int?,
    val sr: Double?
)

data class Bowler(
    val name: String?,
    val o: Double?,
    val m: Int?,
    val r: Int?,
    val w: Int?,
    val econ: Double?
)
