package com.aariz.sportsapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Player(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("firstname")
    val firstName: String,
    
    @SerializedName("lastname")
    val lastName: String,
    
    @SerializedName("fullname")
    val fullName: String,
    
    @SerializedName("image_path")
    val imagePath: String?,
    
    @SerializedName("dateofbirth")
    val dateOfBirth: String?,
    
    @SerializedName("gender")
    val gender: String?,
    
    @SerializedName("battingstyle")
    val battingStyle: String?,
    
    @SerializedName("bowlingstyle")
    val bowlingStyle: String?,
    
    @SerializedName("position")
    val position: Position?,
    
    @SerializedName("country")
    val country: Country?,
    
    @SerializedName("career")
    val career: List<Career>? = null
) : Serializable

data class Position(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String
) : Serializable

data class Country(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("image_path")
    val imagePath: String?
) : Serializable

data class Career(
    @SerializedName("type")
    val type: String,
    
    @SerializedName("season_id")
    val seasonId: Int?,
    
    @SerializedName("batting")
    val batting: CareerBatting?,
    
    @SerializedName("bowling")
    val bowling: CareerBowling?
) : Serializable

data class CareerBatting(
    @SerializedName("matches")
    val matches: Int?,
    
    @SerializedName("innings")
    val innings: Int?,
    
    @SerializedName("runs_scored")
    val runsScored: Int?,
    
    @SerializedName("not_outs")
    val notOuts: Int?,
    
    @SerializedName("highest_inning_score")
    val highestInningScore: Int?,
    
    @SerializedName("strike_rate")
    val strikeRate: Double?,
    
    @SerializedName("balls_faced")
    val ballsFaced: Int?,
    
    @SerializedName("average")
    val average: Double?,
    
    @SerializedName("four_x")
    val fours: Int?,
    
    @SerializedName("six_x")
    val sixes: Int?
) : Serializable

data class CareerBowling(
    @SerializedName("matches")
    val matches: Int?,
    
    @SerializedName("innings")
    val innings: Int?,
    
    @SerializedName("overs")
    val overs: Double?,
    
    @SerializedName("maidens")
    val maidens: Int?,
    
    @SerializedName("runs_conceded")
    val runsConceded: Int?,
    
    @SerializedName("wickets")
    val wickets: Int?,
    
    @SerializedName("average")
    val average: Double?,
    
    @SerializedName("economy_rate")
    val economyRate: Double?,
    
    @SerializedName("strike_rate")
    val strikeRate: Double?
) : Serializable
