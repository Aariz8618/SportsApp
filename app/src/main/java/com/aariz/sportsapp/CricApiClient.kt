package com.aariz.sportsapp.api

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object CricApiClient {
    private const val BASE_URL = "https://api.cricapi.com/v1/"
    private const val TAG = "CricApiClient"

    private val gson = GsonBuilder()
        .setLenient() // Handle malformed JSON
        .serializeNulls() // Include null values
        .create()

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d(TAG, message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Custom interceptor for additional request/response handling
    private val customInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        // Add custom headers if needed
        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")

        val request = requestBuilder.build()

        Log.d(TAG, "Making request to: ${request.url}")

        try {
            val response = chain.proceed(request)
            Log.d(TAG, "Response code: ${response.code}")

            // Log response body for debugging (be careful with large responses)
            if (!response.isSuccessful) {
                val errorBody = response.peekBody(1024).string()
                Log.e(TAG, "Error response body: $errorBody")
            }

            response
        } catch (e: IOException) {
            Log.e(TAG, "Network error during request", e)
            throw e
        }
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(customInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    val apiService: CricApiService by lazy {
        Log.d(TAG, "Initializing API service with base URL: $BASE_URL")

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CricApiService::class.java)
    }

    // Utility function to validate API key
    fun isValidApiKey(apiKey: String?): Boolean {
        return !apiKey.isNullOrBlank() && apiKey.length >= 10
    }

    // Utility function to log API call details
    fun logApiCall(endpoint: String, params: Map<String, Any?>) {
        Log.d(TAG, "API Call - Endpoint: $endpoint")
        params.forEach { (key, value) ->
            Log.d(TAG, "  $key: $value")
        }
    }
}