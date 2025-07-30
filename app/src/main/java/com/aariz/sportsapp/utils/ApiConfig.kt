package com.aariz.sportsapp.utils

object ApiConfig {
    // Cricbuzz API for both Matches and Players
    const val BASE_URL = "https://cricbuzz-cricket.p.rapidapi.com/"
    
    // Note: If this API key returns 403 Forbidden, it might be:
    // 1. Expired or revoked
    // 2. Subscription doesn't include these endpoints
    // 3. Rate limited
    // The app will fallback to mock data if API fails
    const val RAPIDAPI_KEY = "3fc409decdmshf4746b62d74ba65p12725cjsna1b0ee745f3e"
    const val RAPIDAPI_HOST = "cricbuzz-cricket.p.rapidapi.com"
}
