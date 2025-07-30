package com.aariz.sportsapp.model

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("matchScheduleMap")
    val matchScheduleMap: List<ScheduleMapItem>,
    
    @SerializedName("startDt")
    val startDt: List<String>,
    
    @SerializedName("appIndex")
    val appIndex: AppIndex?
)

data class ScheduleMapItem(
    @SerializedName("scheduleAdWrapper")
    val scheduleAdWrapper: ScheduleAdWrapper?,
    
    @SerializedName("adDetail")
    val adDetail: AdDetail?
)

data class ScheduleAdWrapper(
    @SerializedName("date")
    val date: String,
    
    @SerializedName("matchScheduleList")
    val matchScheduleList: List<MatchSchedule>,
    
    @SerializedName("longDate")
    val longDate: String
)

data class MatchSchedule(
    @SerializedName("seriesName")
    val seriesName: String,
    
    @SerializedName("matchInfo")
    val matchInfo: List<MatchInfo>,
    
    @SerializedName("seriesId")
    val seriesId: Int,
    
    @SerializedName("seriesCategory")
    val seriesCategory: String
)

data class MatchInfo(
    @SerializedName("matchId")
    val matchId: Int,
    
    @SerializedName("seriesId")
    val seriesId: Int,
    
    @SerializedName("matchDesc")
    val matchDesc: String,
    
    @SerializedName("matchFormat")
    val matchFormat: String,
    
    @SerializedName("startDate")
    val startDate: String,
    
    @SerializedName("endDate")
    val endDate: String,
    
    @SerializedName("team1")
    val team1: TeamInfo,
    
    @SerializedName("team2")
    val team2: TeamInfo,
    
    @SerializedName("venueInfo")
    val venueInfo: VenueInfo
)

data class TeamInfo(
    @SerializedName("teamId")
    val teamId: Int,
    
    @SerializedName("teamName")
    val teamName: String,
    
    @SerializedName("teamSName")
    val teamSName: String,
    
    @SerializedName("isFullMember")
    val isFullMember: Boolean? = null,
    
    @SerializedName("imageId")
    val imageId: Int
)

data class VenueInfo(
    @SerializedName("ground")
    val ground: String,
    
    @SerializedName("city")
    val city: String,
    
    @SerializedName("country")
    val country: String,
    
    @SerializedName("timezone")
    val timezone: String
)

data class AdDetail(
    @SerializedName("name")
    val name: String,
    
    @SerializedName("layout")
    val layout: String,
    
    @SerializedName("position")
    val position: Int
)

data class AppIndex(
    @SerializedName("seoTitle")
    val seoTitle: String,
    
    @SerializedName("webURL")
    val webURL: String
)
