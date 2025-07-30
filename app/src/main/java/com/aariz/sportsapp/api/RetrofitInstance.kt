package com.aariz.sportsapp.api

import com.aariz.sportsapp.utils.ApiConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Cricbuzz API Retrofit instance for both matches and players
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API service for matches (Cricbuzz)
    val matchApi: CricketApiService by lazy {
        retrofit.create(CricketApiService::class.java)
    }
    
    // API service for players (Cricbuzz)
    val playerApi: CricketApiService by lazy {
        retrofit.create(CricketApiService::class.java)
    }
    
    // Keep the old api for backward compatibility
    val api: CricketApiService by lazy {
        retrofit.create(CricketApiService::class.java)
    }
}

