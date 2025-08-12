package com.aariz.sportsapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CricApiClient {
    private const val BASE_URL = "https://api.cricapi.com/v1/"
    private const val TAG = "CricApiClient"

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d(TAG, message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            Log.d(TAG, "Making request to: ${originalRequest.url}")

            val request = originalRequest.newBuilder()
                .addHeader("User-Agent", "SportsApp/1.0")
                .addHeader("Accept", "application/json")
                .build()

            try {
                val response = chain.proceed(request)
                Log.d(TAG, "Response code: ${response.code}")
                response
            } catch (e: Exception) {
                Log.e(TAG, "Network error: ${e.message}", e)
                throw e
            }
        }
        .build()

    val apiService: CricApiService by lazy {
        Log.d(TAG, "Initializing Retrofit with base URL: $BASE_URL")
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CricApiService::class.java)
    }
}