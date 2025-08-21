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
    @SerializedName("bowlers") val bowlers: List<Bowler>?,
    // Extras: sometimes a total int, sometimes expanded object
    val extras: Int?,
    val extrasData: ExtrasData?,
    // Did not bat / yet to bat lists may come under different keys
    @SerializedName(value = "didNotBat", alternate = ["dnb"]) val didNotBat: List<String>?,
    @SerializedName(value = "yetToBat", alternate = ["ytb"]) val yetToBat: List<String>?,
    // Fall of wickets
    @SerializedName(value = "fows", alternate = ["fallOfWickets"]) val fows: List<Fow>?,
    // Powerplays
    val powerplay: Powerplay?
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

// Expanded breakdown of extras when provided
// All fields nullable; some APIs may omit or rename them
data class ExtrasData(
    val total: Int?,
    @SerializedName(value = "byes", alternate = ["b"]) val byes: Int?,
    @SerializedName(value = "legbyes", alternate = ["lb"]) val legByes: Int?,
    @SerializedName(value = "wides", alternate = ["wd"]) val wides: Int?,
    @SerializedName(value = "noballs", alternate = ["nb"]) val noBalls: Int?,
    val penalty: Int?
)

// Fall of wicket entry
data class Fow(
    // Example format varies, keep common fields
    val batter: String?,
    val score: String?, // e.g., "120/3"
    val over: String?   // e.g., "15.2"
)

// Powerplay ranges if available
data class Powerplay(
    val pp1: String?,
    val pp2: String?,
    val pp3: String?
)
