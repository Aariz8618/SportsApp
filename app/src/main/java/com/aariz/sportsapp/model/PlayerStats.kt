package com.aariz.sportsapp.model

import com.google.gson.annotations.SerializedName

data class PlayerProfile(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickName") val nickName: String,
    @SerializedName("role") val role: String,
    @SerializedName("DoB") val dob: String,
    @SerializedName("image") val image: String,
    @SerializedName("intlTeam") val intlTeam: String,
    @SerializedName("teams") val teams: String
)

data class PlayerCareer(
    @SerializedName("values") val values: List<CareerStat>
)

data class CareerStat(
    @SerializedName("name") val name: String,
    @SerializedName("debut") val debut: String,
    @SerializedName("lastPlayed") val lastPlayed: String
)

data class BattingStats(
    @SerializedName("values") val values: List<BattingStatDetail>
)

data class BattingStatDetail(
    @SerializedName("format") val format: String,
    @SerializedName("matches") val matches: String,
    @SerializedName("innings") val innings: String,
    @SerializedName("runs") val runs: String,
    @SerializedName("avg") val avg: String,
    @SerializedName("sr") val sr: String
)

data class BowlingStats(
    @SerializedName("values") val values: List<BowlingStatDetail>
)

data class BowlingStatDetail(
    @SerializedName("format") val format: String,
    @SerializedName("matches") val matches: String,
    @SerializedName("innings") val innings: String,
    @SerializedName("wickets") val wickets: String,
    @SerializedName("avg") val avg: String,
    @SerializedName("sr") val sr: String
)
