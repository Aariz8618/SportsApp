package com.aariz.sportsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.R
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.models.MatchInfoResponse
import com.aariz.sportsapp.models.MatchInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchDetailsScreenFragment : Fragment() {

    private lateinit var tvDate: TextView
    private lateinit var tvVenue: TextView
    private lateinit var tvDateTime: TextView
    private lateinit var tvTossWinner: TextView
    private lateinit var tvSeriesId: TextView
    private lateinit var tvFantasyEnabled: TextView
    private lateinit var tvBbbEnabled: TextView
    private lateinit var tvHasSquad: TextView

    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"
    private var matchId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get match ID and name from arguments
        arguments?.let {
            matchId = it.getString("match_id")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.matchdetailbtn, container, false)

        initializeViews(view)
        fetchMatchDetails()

        return view
    }

    private fun initializeViews(view: View) {
        tvDate = view.findViewById(R.id.tv_date)
        tvVenue = view.findViewById(R.id.tv_venue)
        tvDateTime = view.findViewById(R.id.tv_date_time)
        tvTossWinner = view.findViewById(R.id.tv_toss_winner)
        tvSeriesId = view.findViewById(R.id.tv_series_id)
        tvFantasyEnabled = view.findViewById(R.id.tv_fantasy_enabled)
        tvBbbEnabled = view.findViewById(R.id.tv_bbb_enabled)
        tvHasSquad = view.findViewById(R.id.tv_has_squad)
    }

    private fun fetchMatchDetails() {
        val currentMatchId = matchId
        if (currentMatchId.isNullOrEmpty()) {
            showError("No match ID provided")
            return
        }
        Log.d("MatchDetailsScreen", "Fetching match details for ID: $currentMatchId")

        CricApiClient.apiService.getMatchInfo(apiKey, currentMatchId)
            .enqueue(object : Callback<MatchInfoResponse> {
                override fun onResponse(
                    call: Call<MatchInfoResponse>,
                    response: Response<MatchInfoResponse>
                ) {
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
                    showError("Network error: ${t.message}")
                }
            })
    }

    private fun displayMatchDetails(matchInfo: MatchInfo) {
        tvDate.text = matchInfo.date ?: "N/A"
        tvVenue.text = matchInfo.venue ?: "N/A"
        tvDateTime.text = matchInfo.dateTimeGMT ?: "N/A"

        val tossInfo = if (matchInfo.tossWinner != null) {
            "${matchInfo.tossWinner} won and chose to ${matchInfo.tossChoice ?: "bat/bowl"}"
        } else {
            "N/A"
        }
        tvTossWinner.text = tossInfo

        tvSeriesId.text = matchInfo.series ?: "N/A"

        // These are usually API-level features, setting defaults for demo
        tvFantasyEnabled.text = "Yes"
        tvBbbEnabled.text = "Yes"
        tvHasSquad.text = if (!matchInfo.players.isNullOrEmpty()) "Yes" else "No"
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        Log.e("MatchDetailsScreen", message)


    }
}