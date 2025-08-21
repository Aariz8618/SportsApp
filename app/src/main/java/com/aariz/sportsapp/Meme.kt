package com.aariz.sportsapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meme(
    val id: Int,
    val imageResource: Int,
    val title: String,
    val description: String = ""
) : Parcelable
