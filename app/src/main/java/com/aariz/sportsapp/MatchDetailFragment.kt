package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.aariz.sportsapp.R
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.models.MatchInfoResponse
import com.aariz.sportsapp.models.MatchInfo
import com.aariz.sportsapp.models.ScorecardResponse
import com.aariz.sportsapp.models.ScorecardData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MatchDetailFragment : Fragment() {

    // UI Components
    private lateinit var tvMatchDate: TextView
    private lateinit var tvMatchStatus: TextView
    private lateinit var tvMatchFormat: TextView
    private lateinit var tvMatchResult: TextView
    private lateinit var tvVenueResult: TextView
    private lateinit var tvMatchTeam1: TextView
    private lateinit var tvMatchTeam2: TextView
    private lateinit var tvTeam1Score: TextView
    private lateinit var tvTeam2Score: TextView
    private var tvHighestScore: TextView? = null
    private var tvHighestScorer: TextView? = null
    private var tvBestBowling: TextView? = null
    private var tvBestBowler: TextView? = null
    private var tvMargin: TextView? = null
    private var btnViewDetails: Button? = null
    private var btnViewSquad: Button? = null
    private var progressLoading: ProgressBar? = null
    private lateinit var tabNavigation: TabLayout

    // API and Data
    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"
    private var matchId: String? = null
    private var matchName: String? = null
    private var seriesId: String? = null

    // Data holders
    private var matchInfo: MatchInfo? = null
    private var scorecardData: ScorecardData? = null

    companion object {
        private const val TAG = "MatchDetailFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            matchId = it.getString("match_id")
            matchName = it.getString("match_name")
        }
        Log.d(TAG, "Fragment created with matchId: $matchId, matchName: $matchName")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_match_detail, container, false)

        initializeViews(view)
        setupClickListeners()
        setupTabs()

        // Start loading data
        setLoading(true)
        fetchMatchDetails()

        return view
    }

    private fun initializeViews(view: View) {
        try {
            // Bind views that exist in fragment_match_detail.xml
            tvMatchStatus = view.findViewById(R.id.badge_live)
            tvMatchFormat = view.findViewById(R.id.badge_format)
            tvMatchResult = view.findViewById(R.id.tv_match_result)
            tvVenueResult = view.findViewById(R.id.tv_venue)
            tvMatchDate = view.findViewById(R.id.tv_date_time)
            tvMatchTeam1 = view.findViewById(R.id.tv_team_a_name)
            tvMatchTeam2 = view.findViewById(R.id.tv_team_b_name)
            tvTeam1Score = view.findViewById(R.id.tv_team_a_score)
            tvTeam2Score = view.findViewById(R.id.tv_team_b_score)
            // Optional highlight views are not present in this layout; keep as null
            tvHighestScore = null
            tvHighestScorer = null
            tvBestBowling = null
            tvBestBowler = null
            tvMargin = null
            // Optional elements (may not exist in this layout)
            btnViewDetails = view.findViewById(R.id.btn_view_details)
            btnViewSquad = view.findViewById(R.id.btn_view_squad)
            progressLoading = view.findViewById(R.id.progress_loading)
            // Use the actual TabLayout ID from the layout
            tabNavigation = view.findViewById(R.id.tabLayout)

            Log.d(TAG, "All views initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing views", e)
            showError("Failed to initialize UI components: ${e.message}")
        }
    }

    private fun setupTabs() {
        // Ensure Summary tab is selected
        tabNavigation.getTabAt(0)?.select()

        tabNavigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 1) {
                    Log.d(TAG, "Switching to Scorecard tab")
                    navigateToScorecard()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupClickListeners() {
        btnViewDetails?.setOnClickListener {
            Log.d(TAG, "View Details button clicked")
            navigateToMatchDetailsScreen()
        }

        btnViewSquad?.setOnClickListener {
            Log.d(TAG, "View Squad button clicked")
            navigateToSquadScreen()
        }
    }

    private fun fetchMatchDetails() {
        val currentMatchId = matchId
        if (currentMatchId.isNullOrEmpty()) {
            Log.e(TAG, "No match ID provided")
            showError("No match ID provided")
            setLoading(false)
            return
        }

        Log.d(TAG, "Starting to fetch match details for ID: $currentMatchId")

        // Fetch match info first
        CricApiClient.apiService.getMatchInfo(apiKey, currentMatchId)
            .enqueue(object : Callback<MatchInfoResponse> {
                override fun onResponse(
                    call: Call<MatchInfoResponse>,
                    response: Response<MatchInfoResponse>
                ) {
                    if (!isAdded) return
                    Log.d(TAG, "Match info response received - Success: ${response.isSuccessful}, Code: ${response.code()}")

                    if (response.isSuccessful) {
                        response.body()?.let { matchInfoResponse ->
                            Log.d(TAG, "Match info response body: $matchInfoResponse")

                            if (matchInfoResponse.status?.equals("success", true) == true) {
                                matchInfoResponse.data?.let { info ->
                                    matchInfo = info
                                    Log.d(TAG, "Match info stored successfully")
                                    displayMatchInfo(info)

                                    // Now fetch scorecard data
                                    fetchScorecardData()
                                } ?: run {
                                    Log.w(TAG, "Match info data is null")
                                    setDefaultMatchInfo()
                                    fetchScorecardData() // Still try to fetch scorecard
                                }
                            } else {
                                Log.e(TAG, "API returned non-success status: ${matchInfoResponse.status}")
                                if (isAdded) {
                                    showError("API Error: ${matchInfoResponse.message ?: "Unknown error"}")
                                }
                                setDefaultMatchInfo()
                                fetchScorecardData()
                            }
                        } ?: run {
                            Log.e(TAG, "Match info response body is null")
                            if (isAdded) {
                                showError("Empty response from server")
                            }
                            setDefaultMatchInfo()
                            fetchScorecardData()
                        }
                    } else {
                        Log.e(TAG, "Match info API error: ${response.code()} - ${response.message()}")
                        if (isAdded) {
                            showError("Failed to load match info: ${response.code()}")
                        }
                        setDefaultMatchInfo()
                        fetchScorecardData()
                    }
                }

                override fun onFailure(call: Call<MatchInfoResponse>, t: Throwable) {
                    if (!isAdded) return
                    Log.e(TAG, "Match info network error", t)
                    showError("Network error: ${t.message}")
                    setDefaultMatchInfo()
                    fetchScorecardData()
                }
            })
    }

    private fun fetchScorecardData() {
        val currentMatchId = matchId
        if (currentMatchId.isNullOrEmpty()) {
            Log.e(TAG, "No match ID for scorecard")
            setDefaultScorecard()
            setLoading(false)
            return
        }

        Log.d(TAG, "Fetching scorecard for match ID: $currentMatchId")

        CricApiClient.apiService.getScorecard(apiKey, currentMatchId)
            .enqueue(object : Callback<ScorecardResponse> {
                override fun onResponse(
                    call: Call<ScorecardResponse>,
                    response: Response<ScorecardResponse>
                ) {
                    if (!isAdded) return
                    setLoading(false)
                    Log.d(TAG, "Scorecard response received - Success: ${response.isSuccessful}, Code: ${response.code()}")

                    if (response.isSuccessful) {
                        response.body()?.let { scorecardResponse ->
                            Log.d(TAG, "Scorecard response body: $scorecardResponse")

                            if (scorecardResponse.status?.equals("success", true) == true) {
                                scorecardResponse.data?.let { data ->
                                    scorecardData = data
                                    Log.d(TAG, "Scorecard data stored successfully")
                                    displayScorecardData(data)
                                } ?: run {
                                    Log.w(TAG, "Scorecard data is null")
                                    setDefaultScorecard()
                                }
                            } else {
                                Log.e(TAG, "Scorecard API returned non-success: ${scorecardResponse.status}")
                                showError("Scorecard Error: ${scorecardResponse.message ?: "Unknown error"}")
                                setDefaultScorecard()
                            }
                        } ?: run {
                            Log.e(TAG, "Scorecard response body is null")
                            setDefaultScorecard()
                        }
                    } else {
                        Log.e(TAG, "Scorecard API error: ${response.code()} - ${response.message()}")
                        setDefaultScorecard()
                    }
                }

                override fun onFailure(call: Call<ScorecardResponse>, t: Throwable) {
                    if (!isAdded) return
                    setLoading(false)
                    Log.e(TAG, "Scorecard network error", t)
                    setDefaultScorecard()
                }
            })
    }

    private fun displayMatchInfo(matchInfo: MatchInfo) {
        Log.d(TAG, "Displaying match info: $matchInfo")

        try {
            // Status
            val rawStatus = matchInfo.status ?: ""
            val normalizedStatus = normalizeStatus(rawStatus)
            tvMatchStatus.text = normalizedStatus
            applyStatusChip(normalizedStatus)

            // Venue with more details
            tvVenueResult.text = matchInfo.venue ?: "Cricket Ground"

            // Enhanced date formatting
            val dateText = buildString {
                val formattedDate = formatMatchDate(matchInfo.date ?: matchInfo.dateTimeGMT ?: "")
                append(formattedDate)
                
                // Add match time if available
                matchInfo.dateTimeGMT?.let { gmtTime ->
                    try {
                        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                        val date = dateFormat.parse(gmtTime.replace("Z", ""))
                        date?.let {
                            append(" • ${timeFormat.format(it)}")
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "Error parsing time: $gmtTime", e)
                    }
                }
            }
            tvMatchDate.text = dateText

            // Match format with series info
            val formatText = buildString {
                append(matchInfo.matchType?.uppercase() ?: "ODI")
                matchInfo.series?.let { series ->
                    append(" • $series")
                }
            }
            tvMatchFormat.text = formatText

            // Teams
            val teams = matchInfo.teams ?: listOf("Team 1", "Team 2")
            if (teams.size >= 2) {
                tvMatchTeam1.text = teams[0]
                tvMatchTeam2.text = teams[1]
            }

            // Enhanced result with toss info
            val resultText = buildString {
                val result = matchInfo.result ?: rawStatus.ifBlank { "Match in progress" }
                append(result)
                
                // Add toss information
                if (!matchInfo.tossWinner.isNullOrBlank() && !matchInfo.tossChoice.isNullOrBlank()) {
                    append("\n🏏 ${matchInfo.tossWinner} won toss, elected to ${matchInfo.tossChoice}")
                }
                
                // Add match started/ended status
                // Removed matchStarted and matchEnded references
            }
            tvMatchResult.text = resultText

            // Store series ID for navigation
            seriesId = matchInfo.seriesId

            Log.d(TAG, "Match info displayed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error displaying match info", e)
            showError("Error displaying match information")
        }
    }

    private fun displayScorecardData(scorecardData: ScorecardData) {
        Log.d(TAG, "Displaying scorecard data: $scorecardData")

        try {
            if (!isAdded) return
            val innings = scorecardData.innings ?: emptyList()
            Log.d(TAG, "Processing ${innings.size} innings")

            if (innings.isNotEmpty()) {
                // Display team scores
                val firstInnings = innings[0]
                val team1Score = formatInningsScore(firstInnings)
                tvTeam1Score.text = team1Score
                Log.d(TAG, "Team 1 score: $team1Score")

                if (innings.size > 1) {
                    val secondInnings = innings[1]
                    val team2Score = formatInningsScore(secondInnings)
                    tvTeam2Score.text = team2Score
                    Log.d(TAG, "Team 2 score: $team2Score")
                } else {
                    tvTeam2Score.text = "Yet to bat"
                }

                // Update match highlights
                updateMatchHighlights(innings)
            } else {
                Log.w(TAG, "No innings data available")
                setDefaultScorecard()
            }

            Log.d(TAG, "Scorecard data displayed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error displaying scorecard data", e)
            setDefaultScorecard()
        }
    }

    private fun formatInningsScore(innings: com.aariz.sportsapp.models.Inning): String {
        val runs = innings.r ?: 0
        val wickets = innings.w ?: 0
        val overs = innings.o ?: 0.0
        return "$runs/$wickets (${String.format("%.1f", overs)} Ov)"
    }

    private fun updateMatchHighlights(innings: List<com.aariz.sportsapp.models.Inning>) {
        var highestScore = 0
        var highestScorer = "No Data"
        var bestBowlingWickets = 0
        var bestBowlingRuns = Int.MAX_VALUE
        var bestBowler = "No Data"
        var bestBowlingFigures = "0/0"
        var marginValue = "0"

        Log.d(TAG, "Calculating match highlights from ${innings.size} innings")

        innings.forEach { inning ->
            // Highest individual score
            inning.batsmen?.forEach { batsman ->
                val runs = batsman.r ?: 0
                if (runs > highestScore) {
                    highestScore = runs
                    highestScorer = batsman.name ?: "Unknown"
                }
            }

            // Best bowling figures
            inning.bowlers?.forEach { bowler ->
                val wickets = bowler.w ?: 0
                val runs = bowler.r ?: 0

                if (wickets > bestBowlingWickets ||
                    (wickets == bestBowlingWickets && runs < bestBowlingRuns && wickets > 0)) {
                    bestBowlingWickets = wickets
                    bestBowlingRuns = runs
                    bestBowler = bowler.name ?: "Unknown"
                    bestBowlingFigures = "$wickets/$runs"
                }
            }
        }

        // Calculate margin from match result if available
        matchInfo?.result?.let { result ->
            marginValue = extractMargin(result)
        }

        // Update UI
        tvHighestScore?.text = if (highestScore > 0) highestScore.toString() else "0"
        tvHighestScorer?.text = highestScorer
        tvBestBowling?.text = bestBowlingFigures
        tvBestBowler?.text = bestBowler
        tvMargin?.text = marginValue

        Log.d(TAG, "Highlights - Highest: $highestScore by $highestScorer, Best bowling: $bestBowlingFigures by $bestBowler, Margin: $marginValue")
    }

    private fun extractMargin(result: String): String {
        return try {
            when {
                result.contains("wicket", true) -> {
                    val match = Regex("(\\d+)\\s+wicket").find(result)
                    match?.groupValues?.get(1) ?: "0"
                }
                result.contains("run", true) -> {
                    val match = Regex("(\\d+)\\s+run").find(result)
                    match?.groupValues?.get(1) ?: "0"
                }
                else -> "0"
            }
        } catch (e: Exception) {
            "0"
        }
    }

    private fun normalizeStatus(status: String): String {
        return when {
            status.contains("live", true) -> "LIVE"
            status.contains("complete", true) || status.contains("finished", true) -> "COMPLETED"
            status.contains("upcoming", true) || status.contains("scheduled", true) -> "UPCOMING"
            else -> status.ifBlank { "UNKNOWN" }.uppercase()
        }
    }

    private fun applyStatusChip(status: String) {
        val backgroundRes = when (status) {
            "LIVE" -> R.drawable.status_chip_live
            "COMPLETED" -> R.drawable.status_chip_completed_green
            "UPCOMING" -> R.drawable.status_chip_upcoming
            else -> R.drawable.status_chip_completed_green
        }
        tvMatchStatus.setBackgroundResource(backgroundRes)
    }

    private fun formatMatchDate(dateString: String): String {
        if (dateString.isBlank()) return "Match Date"

        return try {
            when {
                dateString.contains("T") -> {
                    // ISO format
                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                    val date = sdf.parse(dateString.replace("Z", ""))
                    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date ?: Date())
                }
                dateString.contains("-") -> {
                    // Simple date format
                    val parts = dateString.split("-")
                    if (parts.size == 3) {
                        "${parts[2]} ${getMonthName(parts[1].toInt())} ${parts[0]}"
                    } else dateString
                }
                else -> dateString
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error formatting date: $dateString", e)
            dateString
        }
    }

    private fun getMonthName(month: Int): String {
        return when (month) {
            1 -> "Jan"; 2 -> "Feb"; 3 -> "Mar"; 4 -> "Apr"
            5 -> "May"; 6 -> "Jun"; 7 -> "Jul"; 8 -> "Aug"
            9 -> "Sep"; 10 -> "Oct"; 11 -> "Nov"; 12 -> "Dec"
            else -> "Jan"
        }
    }

    private fun setDefaultMatchInfo() {
        Log.d(TAG, "Setting default match info")
        if (!isAdded) return
        tvVenueResult.text = "Cricket Ground"
        tvMatchDate.text = "Match Date"
        tvMatchStatus.text = "UNKNOWN"
        tvMatchFormat.text = "ODI"
        tvMatchTeam1.text = "Team 1"
        tvMatchTeam2.text = "Team 2"
        tvMatchResult.text = "Match Result"
    }

    private fun setDefaultScorecard() {
        Log.d(TAG, "Setting default scorecard data")
        if (!isAdded) return
        tvTeam1Score.text = "0/0 (0.0 Ov)"
        tvTeam2Score.text = "0/0 (0.0 Ov)"
        tvHighestScore?.text = "0"
        tvHighestScorer?.text = "No Data"
        tvBestBowling?.text = "0/0"
        tvBestBowler?.text = "No Data"
        tvMargin?.text = "0"
    }

    private fun setLoading(visible: Boolean) {
        if (!isAdded) return
        progressLoading?.visibility = if (visible) View.VISIBLE else View.GONE
        Log.d(TAG, "Loading indicator: $visible")
    }

    private fun navigateToScorecard() {
        val fragment = MatchScorecardFragment().apply {
            arguments = Bundle().apply {
                putString("match_id", matchId)
                putString("match_name", matchName)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun navigateToMatchDetailsScreen() {
        val fragment = MatchDetailsScreenFragment().apply {
            arguments = Bundle().apply {
                putString("match_id", matchId)
                putString("match_name", matchName)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToSquadScreen() {
        val fragment = MatchSquadFragment().apply {
            arguments = Bundle().apply {
                putString("match_id", matchId)
                putString("match_name", matchName)
                putString("series_id", seriesId)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showError(message: String) {
        context?.let { ctx ->
            Toast.makeText(ctx, message, Toast.LENGTH_LONG).show()
        }
        Log.e(TAG, message)
    }
}