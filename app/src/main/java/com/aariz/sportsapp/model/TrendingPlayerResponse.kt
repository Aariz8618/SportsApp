package com.aariz.sportsapp.model

import com.google.gson.annotations.SerializedName

data class TrendingPlayerResponse(
    @SerializedName("player")
    val player: List<TrendingPlayer>,
    
    @SerializedName("category")
    val category: String
)

data class TrendingPlayer(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("teamName")
    val teamName: String,
    
    @SerializedName("faceImageId")
    val faceImageId: Int
)
