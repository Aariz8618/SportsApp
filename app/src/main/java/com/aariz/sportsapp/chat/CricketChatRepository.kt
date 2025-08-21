package com.aariz.sportsapp.chat

import android.util.Log
import com.aariz.sportsapp.BuildConfig // Changed this import
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.api.CricApiService
import com.aariz.sportsapp.models.Player
import com.aariz.sportsapp.models.UpcomingMatch
import com.aariz.sportsapp.models.MatchInfo
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import java.net.SocketTimeoutException
import java.io.IOException

class CricketChatRepository(
    private val cricApi: CricApiService = CricApiClient.apiService,
    private val cricketApiKey: String = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82", // existing app key
) {
    private val tag: String = "CricketChatRepo"

    private val gemini: GenerativeModel by lazy {
        val key: String = BuildConfig.GEMINI_API_KEY
        require(key.isNotBlank()) { "GEMINI_API_KEY is missing. Add GEMINI_API_KEY to gradle.properties or env." }
        GenerativeModel(modelName = "gemini-1.5-flash", apiKey = key)
    }

    suspend fun answer(query: String): String = withContext(Dispatchers.IO) {
        try {
            val intent: DetectedIntent = detectIntent(query)
            when (intent.type) {
                IntentType.RULES, IntentType.HISTORY -> answerWithGemini(query)
                IntentType.ABOUT_PLAYER, IntentType.PLAYER_STATS -> answerPlayer(intent, query)
                IntentType.MATCH_RESULT -> answerMatchResult(intent)
                IntentType.GENERAL -> answerWithGemini("You are a cricket expert assistant. Keep answers concise.\nQuestion: $query")
            }
        } catch (e: UnknownHostException) {
            Log.e(tag, "No internet connection", e)
            "❌ No internet connection. Please check your network and try again."
        } catch (e: SocketTimeoutException) {
            Log.e(tag, "Network timeout", e)
            "⏱️ Network timeout. Please check your connection and try again."
        } catch (e: IOException) {
            Log.e(tag, "Network error", e)
            "🌐 Network error. Please check your internet connection and try again."
        } catch (e: Exception) {
            Log.e(tag, "General error", e)
            "😔 Sorry, I'm having trouble processing your request right now. Please try again later."
        }
    }

    private suspend fun answerWithGemini(prompt: String): String {
        return try {
            val resp = gemini.generateContent(prompt)
            resp.text?.trim().orEmpty().ifBlank { "Sorry, I couldn't find that." }
        } catch (e: UnknownHostException) {
            throw e // Re-throw to be caught by main error handler
        } catch (e: SocketTimeoutException) {
            throw e // Re-throw to be caught by main error handler
        } catch (e: IOException) {
            throw e // Re-throw to be caught by main error handler
        } catch (e: Exception) {
            Log.e(tag, "Gemini API error", e)
            "Sorry, I'm having trouble accessing the AI service right now."
        }
    }

    private suspend fun answerPlayer(intent: DetectedIntent, original: String): String {
        val name: String? = intent.playerName ?: extractPlayerName(original)
        if (name.isNullOrBlank()) return answerWithGemini(original)

        return try {
            // 1) Search player ID
            val playersResp = cricApi.getPlayers(cricketApiKey, offset = 0, searchQuery = name).execute()
            if (!playersResp.isSuccessful) {
                return "❌ Unable to fetch player data. Please check your internet connection."
            }
            
            val player: Player? = playersResp.body()?.data?.firstOrNull()

            if (player == null) {
                return "Sorry, I couldn't find a player named $name."
            }

            // 2) Fetch player details
            val detailResp = cricApi.getPlayerInfo(cricketApiKey, player.id).execute()
            if (!detailResp.isSuccessful) {
                return "❌ Unable to fetch detailed player information. Please try again."
            }
            
            val detail = detailResp.body()?.data

            // 3) Summarize with Gemini for natural response
            val summaryPrompt = buildString {
                appendLine("You are a cricket stats explainer. Answer concisely in 2-4 lines.")
                appendLine("Question: $original")
                appendLine("Player basic: ${player.name}")
                appendLine("Raw JSON (if present). Keep only relevant figures in answer:\n${detail?.toString()}")
            }
            answerWithGemini(summaryPrompt)
        } catch (e: Exception) {
            Log.e(tag, "Error fetching player data", e)
            throw e // Re-throw to be caught by main error handler
        }
    }

    private suspend fun answerMatchResult(intent: DetectedIntent): String {
        val t1: String = intent.teamA ?: return "Please specify the two teams."
        val t2: String = intent.teamB ?: return "Please specify the two teams."

        return try {
            // Fetch list and pick the most relevant recent result
            val matchesResp = cricApi.getMatchesList(cricketApiKey, offset = 0).execute()
            if (!matchesResp.isSuccessful) {
                return "❌ Unable to fetch match data. Please check your internet connection."
            }
            
            val matches: List<UpcomingMatch> = matchesResp.body()?.data.orEmpty()

            val target: UpcomingMatch? = matches.firstOrNull { m ->
                val n = (m.name ?: "").lowercase()
                n.contains(t1.lowercase()) && n.contains(t2.lowercase()) && !(m.status ?: "").contains("scheduled", true)
            } ?: matches.firstOrNull { m ->
                val n = (m.name ?: "").lowercase()
                (n.contains(t1.lowercase()) || n.contains(teamAliases(t1))) &&
                (n.contains(t2.lowercase()) || n.contains(teamAliases(t2))) && !(m.status ?: "").contains("scheduled", true)
            }

            val matchId: String? = target?.id
            if (matchId.isNullOrBlank()) return "I couldn't find a recent $t1 vs $t2 result."

            val infoResp = cricApi.getMatchInfo(cricketApiKey, matchId).execute()
            if (!infoResp.isSuccessful) {
                return "❌ Unable to fetch match details. Please try again."
            }
            
            val info: MatchInfo? = infoResp.body()?.data
            val result: String = info?.result ?: info?.status ?: "Result not available"

            // Normalize result to simple sentence
            simplifyResultSentence(result)
        } catch (e: Exception) {
            Log.e(tag, "Error fetching match result", e)
            throw e // Re-throw to be caught by main error handler
        }
    }

    private fun simplifyResultSentence(result: String): String {
        val r: String = result.trim()
        if (r.contains("won", true)) return r
        return r
    }

    // --- Intent detection ---
    private fun detectIntent(query: String): DetectedIntent {
        val q: String = query.lowercase()
        return when {
            q.contains("law") || q.contains("rule") || q.contains("no-ball") || q.contains("lbw") -> DetectedIntent(IntentType.RULES)
            q.contains("history") || q.contains("when did cricket") -> DetectedIntent(IntentType.HISTORY)
            q.startsWith("who won between") || q.startsWith("who won in") || q.contains("who won between") -> parseMatchQuery(q)
            q.contains("about ") || q.contains("who is ") || q.contains("career of") -> DetectedIntent(IntentType.ABOUT_PLAYER, playerName = extractPlayerName(query))
            q.contains("stats of") || q.contains("average of") || q.contains("strike rate") -> DetectedIntent(IntentType.PLAYER_STATS, playerName = extractPlayerName(query))
            else -> DetectedIntent(IntentType.GENERAL)
        }
    }

    private fun parseMatchQuery(q: String): DetectedIntent {
        val normalized: String = q.replace("india", "ind", true).replace("pakistan", "pak", true)
        val parts: String = normalized.split("between").getOrNull(1)?.trim() ?: normalized
        val teamTokens: List<String> = parts
            .replace(" vs ", " ")
            .replace(" and ", " ")
            .split(" ")
            .filter { it.isNotBlank() }
        val t1: String? = teamTokens.getOrNull(0)
        val t2: String? = teamTokens.getOrNull(1)
        return DetectedIntent(IntentType.MATCH_RESULT, teamA = t1, teamB = t2)
    }

    private fun extractPlayerName(q: String): String? {
        val lower: String = q.lowercase()
        val idx = listOf("about ", "who is ", "stats of ", "career of ")
            .firstNotNullOfOrNull { k -> lower.indexOf(k).takeIf { it >= 0 }?.let { it to k } }
        return idx?.let { (pos, key) -> q.substring(pos + key.length).trim() }
    }

    private fun teamAliases(t: String): String = when (t.lowercase()) {
        "india", "ind" -> "ind"
        "pakistan", "pak" -> "pak"
        "australia", "aus" -> "aus"
        "england", "eng" -> "eng"
        else -> t.lowercase()
    }
}

private data class DetectedIntent(
    val type: IntentType,
    val playerName: String? = null,
    val teamA: String? = null,
    val teamB: String? = null
)

enum class IntentType { GENERAL, RULES, HISTORY, ABOUT_PLAYER, PLAYER_STATS, MATCH_RESULT }
