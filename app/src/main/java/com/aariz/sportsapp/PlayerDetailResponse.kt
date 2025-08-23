package com.aariz.sportsapp.models

data class PlayerDetailResponse(
    val status: String,
    val data: PlayerDetail?
)

data class PlayerDetail(
    val id: String,
    val name: String,
    val role: String?,
    val battingStyle: String?,
    val bowlingStyle: String?,
    val placeOfBirth: String?,
    val country: String?,
    val playerImg: String?,
    val stats: List<PlayerStatItem>? // Array of individual stat items
) {
    // Helper functions to extract meaningful stats
    fun getMatches(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "m"
        }?.value?.trim() ?: "0"
    }

    fun getInnings(matchType: String = "test", type: String = "batting"): String {
        return stats?.find {
            it.fn == type && it.matchtype == matchType && it.stat?.trim() == "inn"
        }?.value?.trim() ?: "0"
    }

    fun getRuns(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "runs"
        }?.value?.trim() ?: "0"
    }

    fun getWickets(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "wkts"
        }?.value?.trim() ?: "0"
    }

    fun getBattingAverage(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "avg"
        }?.value?.trim() ?: "0"
    }

    fun getBowlingAverage(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "avg"
        }?.value?.trim() ?: "0.0"
    }

    fun getStrikeRate(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "sr"
        }?.value?.trim() ?: "0"
    }

    fun getBallsFaced(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "bf"
        }?.value?.trim() ?: "0"
    }

    fun getHundreds(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "100"
        }?.value?.trim() ?: "0"
    }

    fun getFifties(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "50"
        }?.value?.trim() ?: "0"
    }

    fun getFours(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "4s"
        }?.value?.trim() ?: "0"
    }

    fun getSixes(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "6s"
        }?.value?.trim() ?: "0"
    }

    fun getNotOuts(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "no"
        }?.value?.trim() ?: "0"
    }

    fun getHighScore(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "hs"
        }?.value?.trim() ?: "-"
    }

    fun getDoubleHundreds(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "batting" && it.matchtype == matchType && it.stat?.trim() == "200"
        }?.value?.trim() ?: "0"
    }

    // Bowling specific stats
    fun getBowlingRuns(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "runs"
        }?.value?.trim() ?: "0"
    }

    fun getEconomy(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "econ"
        }?.value?.trim() ?: "0.0"
    }

    fun getBowlingStrikeRate(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "sr"
        }?.value?.trim() ?: "0"
    }

    fun getBallsBowled(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "balls"
        }?.value?.trim() ?: "0"
    }

    fun getBestBowlingInnings(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "bbi"
        }?.value?.trim() ?: "-"
    }

    fun getBestBowlingMatch(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "bbm"
        }?.value?.trim() ?: "-"
    }

    fun getFourWickets(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "4w"
        }?.value?.trim() ?: "0"
    }

    fun getFiveWickets(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "5w"
        }?.value?.trim() ?: "0"
    }

    fun getTenWickets(matchType: String = "test"): String {
        return stats?.find {
            it.fn == "bowling" && it.matchtype == matchType && it.stat?.trim() == "10w"
        }?.value?.trim() ?: "0"
    }

    // Get overall stats (combines different match types)
    fun getTotalMatches(): Int {
        val matchTypes = listOf("test", "odi", "t20", "ipl")
        return matchTypes.sumOf {
            getMatches(it).toIntOrNull() ?: 0
        }
    }

    fun getTotalRuns(): Int {
        val matchTypes = listOf("test", "odi", "t20", "ipl")
        return matchTypes.sumOf {
            getRuns(it).toIntOrNull() ?: 0
        }
    }

    fun getTotalWickets(): Int {
        val matchTypes = listOf("test", "odi", "t20", "ipl")
        return matchTypes.sumOf {
            getWickets(it).toIntOrNull() ?: 0
        }
    }

    // Helper function to check if player has significant stats in a format
    fun hasSignificantStats(matchType: String): Boolean {
        val matches = getMatches(matchType).toIntOrNull() ?: 0
        val runs = getRuns(matchType).toIntOrNull() ?: 0
        val wickets = getWickets(matchType).toIntOrNull() ?: 0
        return matches > 0 || runs > 0 || wickets > 0
    }
}

data class PlayerStatItem(
    val fn: String?, // "batting" or "bowling"
    val matchtype: String?, // "test", "odi", "t20", "ipl"
    val stat: String?, // " m ", " runs ", " wkts ", " avg ", etc.
    val value: String? // The actual stat value
)