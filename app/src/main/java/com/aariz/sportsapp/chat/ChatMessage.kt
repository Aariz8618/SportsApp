package com.aariz.sportsapp.chat

data class ChatMessage(
    val id: Long = System.currentTimeMillis(),
    val role: Role,
    val text: String,
    val isLoading: Boolean = false,
    val prefix: String? = null
) {
    enum class Role { USER, BOT }
}
