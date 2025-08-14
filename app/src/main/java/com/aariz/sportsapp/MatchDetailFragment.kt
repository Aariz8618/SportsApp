package com.aariz.sportsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.aariz.sportsapp.R
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.models.MatchInfoResponse
import com.aariz.sportsapp.models.MatchInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchDetailFragment : Fragment() {

    private lateinit var tvMatchStatus: TextView
    private lateinit var tvSeriesName: TextView
    private lateinit var tvMatchTeam1: TextView
    private lateinit var tvMatchTeam2: TextView
    private lateinit var tvMatchFormat: TextView
    // Removed inline score preview views; scorecard is now in a separate fragment
    private lateinit var tvMatchResult: TextView
    private lateinit var btnViewDetails: Button
    private lateinit var btnViewSquad: Button
    private lateinit var progressLoading: ProgressBar
    private lateinit var tabNavigation: TabLayout

    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"
    private var matchId: String? = null
    private var matchName: String? = null
    private var seriesId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get match ID and name from arguments
        arguments?.let {
            matchId = it.getString("match_id")
            matchName = it.getString("match_name")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_match_detail, container, false)

        initializeViews(view)
        setupClickListeners()
        setLoading(true)
        fetchMatchDetails()

        return view
    }

    private fun initializeViews(view: View) {
        tvMatchStatus = view.findViewById(R.id.tv_match_status)
        tvSeriesName = view.findViewById(R.id.tv_series_name)
        tvMatchTeam1 = view.findViewById(R.id.tv_match_team1)
        tvMatchTeam2 = view.findViewById(R.id.tv_match_team2)
        tvMatchFormat = view.findViewById(R.id.tv_match_format)
        // Inline score preview removed from Summary layout
        tvMatchResult = view.findViewById(R.id.tv_match_result)
        btnViewDetails = view.findViewById(R.id.btn_view_details)
        btnViewSquad = view.findViewById(R.id.btn_view_squad)
        progressLoading = view.findViewById(R.id.progress_loading)
        tabNavigation = view.findViewById(R.id.tab_navigation)

        // Ensure Summary tab is selected when this fragment shows
        tabNavigation.getTabAt(0)?.select()
        tabNavigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 1) {
                    // Navigate to Scorecard tab
                    val frag = MatchScorecardFragment().apply {
                        arguments = Bundle().apply {
                            putString("match_id", matchId)
                            putString("match_name", matchName)
                        }
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, frag)
                        .commit()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupClickListeners() {
        btnViewDetails.setOnClickListener {
            navigateToMatchDetailsScreen()
        }

        btnViewSquad.setOnClickListener {
            navigateToSquadScreen()
        }
    }

    private fun navigateToMatchDetailsScreen() {
        val matchDetailsFragment = MatchDetailsScreenFragment().apply {
            arguments = Bundle().apply {
                putString("match_id", matchId)
                putString("match_name", matchName)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, matchDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToSquadScreen() {
        val squadFragment = MatchSquadFragment().apply {
            arguments = Bundle().apply {
                putString("match_id", matchId)
                putString("match_name", matchName)
                putString("series_id", seriesId)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, squadFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun fetchMatchDetails() {
        val currentMatchId = matchId
        if (currentMatchId.isNullOrEmpty()) {
            showError("No match ID provided")
            return
        }

        Log.d("MatchDetailFragment", "Fetching details for match ID: $currentMatchId")

        CricApiClient.apiService.getMatchInfo(apiKey, currentMatchId)
            .enqueue(object : Callback<MatchInfoResponse> {
                override fun onResponse(
                    call: Call<MatchInfoResponse>,
                    response: Response<MatchInfoResponse>
                ) {
                    setLoading(false)
                    if (response.isSuccessful) {
                        response.body()?.let { matchInfoResponse ->
                            matchInfoResponse.data?.let { matchInfo ->
                                displayMatchDetails(matchInfo)
                            } ?: showError("No match data found")
                        } ?: showError("Response body is null")
                    } else {
                        showError("API error: ${response.code()} - ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<MatchInfoResponse>, t: Throwable) {
                    setLoading(false)
                    showError("Network error: ${t.message}")
                }
            })
    }

    private fun displayMatchDetails(matchInfo: MatchInfo) {
        // Normalize and set match status badge (LIVE / COMPLETED / UPCOMING)
        val rawStatus = matchInfo.status ?: ""
        val normalized = when {
            rawStatus.contains("live", true) -> "LIVE"
            rawStatus.contains("complete", true) || rawStatus.contains("finished", true) -> "COMPLETED"
            rawStatus.contains("upcoming", true) || rawStatus.contains("scheduled", true) || rawStatus.contains("start", true) -> "UPCOMING"
            else -> rawStatus.ifBlank { "UNKNOWN" }.uppercase()
        }
        tvMatchStatus.text = normalized
        applyStatusChip(normalized)

        // Set team names in header
        val teams = matchInfo.teams ?: listOf("Team 1", "Team 2")
        if (teams.size >= 2) {
            tvMatchTeam1.text = teams[0]
            tvMatchTeam2.text = teams[1]
        }

        // Set match format
        tvMatchFormat.text = matchInfo.matchType ?: "Unknown"

        // Set series name and cache seriesId for squad screen
        tvSeriesName.text = matchInfo.series ?: ""
        seriesId = matchInfo.seriesId

        // Inline score preview removed; full scorecard available in Scorecard tab

        // Show what was previously in match status as the match result (per requirement)
        tvMatchResult.text = rawStatus.ifBlank { matchInfo.result ?: "" }.ifBlank { "Match status unavailable" }
    }

    private fun applyStatusChip(normalizedStatus: String) {
        val resId = when (normalizedStatus) {
            "LIVE" -> R.drawable.status_chip_live
            "COMPLETED" -> R.drawable.status_chip_completed
            "UPCOMING" -> R.drawable.status_chip_upcoming
            else -> R.drawable.status_background
        }
        tvMatchStatus.setBackgroundResource(resId)
    }

    private fun setLoading(visible: Boolean) {
        progressLoading.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        Log.e("MatchDetailFragment", message)
    }
}