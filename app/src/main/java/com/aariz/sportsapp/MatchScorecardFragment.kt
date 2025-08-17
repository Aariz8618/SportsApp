package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.models.ScorecardResponse
import com.aariz.sportsapp.models.ScorecardData
import com.aariz.sportsapp.models.MatchInfoResponse
import com.aariz.sportsapp.models.MatchInfo
import com.aariz.sportsapp.models.Inning
import com.aariz.sportsapp.adapters.BattingAdapter
import com.aariz.sportsapp.adapters.BowlingAdapter
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchScorecardFragment : Fragment() {

    // UI Components
    private lateinit var tabNavigation: TabLayout
    private lateinit var tvIndiaScoreHeader: TextView
    private lateinit var tvAustraliaScoreHeader: TextView
    private lateinit var rvIndiaBatting: RecyclerView
    private lateinit var rvIndiaBowling: RecyclerView
    private lateinit var rvAustraliaBatting: RecyclerView
    private lateinit var rvAustraliaBowling: RecyclerView
    private lateinit var progressLoading: ProgressBar

    // Adapters
    private lateinit var indiaBattingAdapter: BattingAdapter
    private lateinit var indiaBowlingAdapter: BowlingAdapter
    private lateinit var australiaBattingAdapter: BattingAdapter
    private lateinit var australiaBowlingAdapter: BowlingAdapter

    // Data
    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"
    private var matchId: String? = null
    private var matchName: String? = null
    private var matchInfo: MatchInfo? = null
    private var scorecardData: ScorecardData? = null

    companion object {
        private const val TAG = "MatchScorecardFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            matchId = it.getString("match_id")
            matchName = it.getString("match_name")
        }
        Log.d(TAG, "Fragment created with matchId: $matchId")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_match_scorecard, container, false)

        initializeViews(view)
        setupRecyclerViews()
        setupTabs()

        setLoading(true)
        fetchMatchInfo()
        fetchScorecardData()

        return view
    }

    private fun initializeViews(view: View) {
        try {
            tabNavigation = view.findViewById(R.id.tab_navigation)
            tvIndiaScoreHeader = view.findViewById(R.id.tv_india_score_header)
            tvAustraliaScoreHeader = view.findViewById(R.id.tv_australia_score_header)
            rvIndiaBatting = view.findViewById(R.id.rv_india_batting)
            rvIndiaBowling = view.findViewById(R.id.rv_india_bowling)
            rvAustraliaBatting = view.findViewById(R.id.rv_australia_batting)
            rvAustraliaBowling = view.findViewById(R.id.rv_australia_bowling)
            progressLoading = view.findViewById(R.id.progress_loading)

            Log.d(TAG, "Views initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing views", e)
            showError("Failed to initialize scorecard view")
        }
    }

    private fun setupRecyclerViews() {
        try {
            // Initialize adapters
            indiaBattingAdapter = BattingAdapter(emptyList())
            indiaBowlingAdapter = BowlingAdapter(emptyList())
            australiaBattingAdapter = BattingAdapter(emptyList())
            australiaBowlingAdapter = BowlingAdapter(emptyList())

            // Setup RecyclerViews
            rvIndiaBatting.apply {
                adapter = indiaBattingAdapter
                layoutManager = LinearLayoutManager(context)
                isNestedScrollingEnabled = false
            }

            rvIndiaBowling.apply {
                adapter = indiaBowlingAdapter
                layoutManager = LinearLayoutManager(context)
                isNestedScrollingEnabled = false
            }

            rvAustraliaBatting.apply {
                adapter = australiaBattingAdapter
                layoutManager = LinearLayoutManager(context)
                isNestedScrollingEnabled = false
            }

            rvAustraliaBowling.apply {
                adapter = australiaBowlingAdapter
                layoutManager = LinearLayoutManager(context)
                isNestedScrollingEnabled = false
            }

            Log.d(TAG, "RecyclerViews setup successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up RecyclerViews", e)
        }
    }

    private fun setupTabs() {
        // Select Scorecard tab
        tabNavigation.getTabAt(1)?.select()

        tabNavigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    Log.d(TAG, "Switching back to Summary tab")
                    navigateToSummary()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun fetchMatchInfo() {
        val currentMatchId = matchId
        if (currentMatchId.isNullOrEmpty()) {
            Log.e(TAG, "No match ID provided for match info")
            showError("No match ID provided")
            return
        }

        Log.d(TAG, "Fetching match info for ID: $currentMatchId")

        CricApiClient.apiService.getMatchInfo(apiKey, currentMatchId)
            .enqueue(object : Callback<MatchInfoResponse> {
                override fun onResponse(
                    call: Call<MatchInfoResponse>,
                    response: Response<MatchInfoResponse>
                ) {
                    Log.d(TAG, "Match info response - Success: ${response.isSuccessful}, Code: ${response.code()}")

                    if (response.isSuccessful) {
                        response.body()?.let { matchInfoResponse ->
                            if (matchInfoResponse.status?.equals("success", true) == true) {
                                matchInfoResponse.data?.let { info ->
                                    matchInfo = info
                                    Log.d(TAG, "Match info stored successfully")
                                    // Update UI with basic match info if needed
                                } ?: Log.w(TAG, "Match info data is null")
                            } else {
                                Log.e(TAG, "Match info API returned non-success: ${matchInfoResponse.status}")
                            }
                        } ?: Log.e(TAG, "Match info response body is null")
                    } else {
                        Log.e(TAG, "Match info API error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MatchInfoResponse>, t: Throwable) {
                    Log.e(TAG, "Match info network error", t)
                }
            })
    }

    private fun fetchScorecardData() {
        val currentMatchId = matchId
        if (currentMatchId.isNullOrEmpty()) {
            Log.e(TAG, "No match ID provided for scorecard")
            showError("No match ID provided")
            setLoading(false)
            return
        }

        Log.d(TAG, "Fetching scorecard for ID: $currentMatchId")

        CricApiClient.apiService.getScorecard(apiKey, currentMatchId)
            .enqueue(object : Callback<ScorecardResponse> {
                override fun onResponse(
                    call: Call<ScorecardResponse>,
                    response: Response<ScorecardResponse>
                ) {
                    setLoading(false)
                    Log.d(TAG, "Scorecard response - Success: ${response.isSuccessful}, Code: ${response.code()}")

                    if (response.isSuccessful) {
                        response.body()?.let { scorecardResponse ->
                            Log.d(TAG, "Scorecard response: $scorecardResponse")

                            if (scorecardResponse.status?.equals("success", true) == true) {
                                scorecardResponse.data?.let { data ->
                                    scorecardData = data
                                    Log.d(TAG, "Scorecard data received with ${data.innings?.size ?: 0} innings")
                                    displayScorecardData(data)
                                } ?: run {
                                    Log.w(TAG, "Scorecard data is null")
                                    showError("No scorecard data found")
                                    setDefaultData()
                                }
                            } else {
                                Log.e(TAG, "Scorecard API non-success: ${scorecardResponse.status}")
                                showError("Scorecard Error: ${scorecardResponse.message ?: "Unknown error"}")
                                setDefaultData()
                            }
                        } ?: run {
                            Log.e(TAG, "Scorecard response body is null")
                            showError("Empty response from server")
                            setDefaultData()
                        }
                    } else {
                        Log.e(TAG, "Scorecard API error: ${response.code()} - ${response.message()}")
                        showError("Failed to load scorecard: ${response.code()}")
                        setDefaultData()
                    }
                }

                override fun onFailure(call: Call<ScorecardResponse>, t: Throwable) {
                    setLoading(false)
                    Log.e(TAG, "Scorecard network error", t)
                    showError("Network error: ${t.message}")
                    setDefaultData()
                }
            })
    }

    private fun displayScorecardData(scorecardData: ScorecardData) {
        Log.d(TAG, "Displaying scorecard data")

        try {
            val innings = scorecardData.innings ?: emptyList()
            val teams = scorecardData.teams ?: listOf("Team 1", "Team 2")

            Log.d(TAG, "Processing ${innings.size} innings for teams: $teams")

            if (innings.isEmpty()) {
                Log.w(TAG, "No innings data available")
                setDefaultData()
                return
            }

            // Update team headers with scores
            if (teams.size >= 2) {
                when (innings.size) {
                    1 -> {
                        // Only first innings available
                        val firstInnings = innings[0]
                        updateTeamHeader(tvIndiaScoreHeader, teams[0], firstInnings)
                        tvAustraliaScoreHeader.text = "${teams[1]} - Yet to bat"

                        // Display first innings data
                        displayInningsData(firstInnings, teams[0])
                    }
                    2 -> {
                        // Both innings available
                        val firstInnings = innings[0]
                        val secondInnings = innings[1]

                        updateTeamHeader(tvIndiaScoreHeader, teams[0], firstInnings)
                        updateTeamHeader(tvAustraliaScoreHeader, teams[1], secondInnings)

                        // Display both innings data
                        displayInningsData(firstInnings, teams[0])
                        displayInningsData(secondInnings, teams[1])
                    }
                    else -> {
                        Log.w(TAG, "Unexpected number of innings: ${innings.size}")
                        // Handle multiple innings (like test matches)
                        val firstInnings = innings[0]
                        val secondInnings = if (innings.size > 1) innings[1] else firstInnings

                        updateTeamHeader(tvIndiaScoreHeader, teams[0], firstInnings)
                        updateTeamHeader(tvAustraliaScoreHeader, teams[1], secondInnings)

                        displayInningsData(firstInnings, teams[0])
                        displayInningsData(secondInnings, teams[1])
                    }
                }
            } else {
                Log.w(TAG, "Insufficient team information")
                setDefaultData()
            }

            Log.d(TAG, "Scorecard data displayed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error displaying scorecard data", e)
            showError("Error displaying scorecard: ${e.message}")
            setDefaultData()
        }
    }

    private fun updateTeamHeader(headerView: TextView, teamName: String, innings: Inning) {
        val runs = innings.r ?: 0
        val wickets = innings.w ?: 0
        val overs = innings.o ?: 0.0
        val scoreText = "$teamName - $runs/$wickets (${String.format("%.1f", overs)} Ov)"
        headerView.text = scoreText
        Log.d(TAG, "Updated header: $scoreText")
    }

    private fun displayInningsData(innings: Inning, teamName: String) {
        Log.d(TAG, "Displaying innings data for $teamName")

        val batsmen = innings.batsmen ?: emptyList()
        val bowlers = innings.bowlers ?: emptyList()

        Log.d(TAG, "Batsmen: ${batsmen.size}, Bowlers: ${bowlers.size}")

        // Determine which adapters to update based on team name
        // This is a simple approach - you might want to improve this logic
        when {
            teamName.contains("india", ignoreCase = true) ||
                    innings.inning?.contains("IND", ignoreCase = true) == true -> {
                indiaBattingAdapter.updateBatsmen(batsmen)
                indiaBowlingAdapter.updateBowlers(bowlers)
                Log.d(TAG, "Updated India adapters")
            }
            teamName.contains("australia", ignoreCase = true) ||
                    innings.inning?.contains("AUS", ignoreCase = true) == true -> {
                australiaBattingAdapter.updateBatsmen(batsmen)
                australiaBowlingAdapter.updateBowlers(bowlers)
                Log.d(TAG, "Updated Australia adapters")
            }
            else -> {
                // Default to first team for first innings, second for second innings
                val isFirstInnings = scorecardData?.innings?.indexOf(innings) == 0
                if (isFirstInnings) {
                    indiaBattingAdapter.updateBatsmen(batsmen)
                    indiaBowlingAdapter.updateBowlers(bowlers)
                    Log.d(TAG, "Updated India adapters (default first innings)")
                } else {
                    australiaBattingAdapter.updateBatsmen(batsmen)
                    australiaBowlingAdapter.updateBowlers(bowlers)
                    Log.d(TAG, "Updated Australia adapters (default second innings)")
                }
            }
        }
    }

    private fun setDefaultData() {
        Log.d(TAG, "Setting default scorecard data")

        tvIndiaScoreHeader.text = "Team 1 - 0/0 (0.0 Ov)"
        tvAustraliaScoreHeader.text = "Team 2 - 0/0 (0.0 Ov)"

        // Clear all adapters
        indiaBattingAdapter.updateBatsmen(emptyList())
        indiaBowlingAdapter.updateBowlers(emptyList())
        australiaBattingAdapter.updateBatsmen(emptyList())
        australiaBowlingAdapter.updateBowlers(emptyList())
    }

    private fun setLoading(visible: Boolean) {
        progressLoading.visibility = if (visible) View.VISIBLE else View.GONE
        Log.d(TAG, "Loading indicator: $visible")
    }

    private fun navigateToSummary() {
        val fragment = MatchDetailFragment().apply {
            arguments = Bundle().apply {
                putString("match_id", matchId)
                putString("match_name", matchName)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        Log.e(TAG, message)
    }
}