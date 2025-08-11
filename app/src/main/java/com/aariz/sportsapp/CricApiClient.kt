package com.aariz.sportsapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CricApiClient {
    private const val BASE_URL = "https://api.cricapi.com/v1/"

    val apiService: CricApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CricApiService::class.java)
    }
}
