package com.aariz.sportsapp.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("data")
    val data: List<Player>,
    
    @SerializedName("meta")
    val meta: Meta?
)

data class Meta(
    @SerializedName("pagination")
    val pagination: Pagination?
)

data class Pagination(
    @SerializedName("total")
    val total: Int?,
    
    @SerializedName("count")
    val count: Int?,
    
    @SerializedName("per_page")
    val perPage: Int?,
    
    @SerializedName("current_page")
    val currentPage: Int?,
    
    @SerializedName("total_pages")
    val totalPages: Int?
)
