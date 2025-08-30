package com.aariz.sportsapp.model

data class SimpleShow(
    val title: String,
    val imageUrl: String,
    val category: String = "" // Changed from 'genre' to 'category' to match your usage
)