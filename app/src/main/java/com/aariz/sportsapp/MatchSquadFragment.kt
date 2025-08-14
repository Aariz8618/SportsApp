package com.aariz.sportsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.adapters.SquadAdapter
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.models.SquadInfoResponse
import com.aariz.sportsapp.models.SquadInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchSquadFragment : Fragment() {

    private lateinit var tvSquadHeader: TextView
    private lateinit var rvSquadList: RecyclerView
    private lateinit var squadAdapter: SquadAdapter

    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"
    private var matchId: String? = null
    private var seriesId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.match_squad, container, false)

        arguments?.let {
            matchId = it.getString("match_id")
            seriesId = it.getString("series_id")
        }

        initializeViews(view)
        setupRecyclerView()
        fetchSquadInfo()

        return view
    }

    private fun initializeViews(view: View) {
        tvSquadHeader = view.findViewById(R.id.tv_squad_header)
        rvSquadList = view.findViewById(R.id.rv_squad_list)
    }

    private fun setupRecyclerView() {
        squadAdapter = SquadAdapter(emptyList())
        rvSquadList.adapter = squadAdapter
        rvSquadList.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun fetchSquadInfo() {
        val sId = seriesId
        val mId = matchId

        if (!sId.isNullOrBlank()) {
            Log.d("MatchSquadFragment", "Fetching series squad for seriesId: $sId")
            CricApiClient.apiService.getSeriesSquad(apiKey, sId)
                .enqueue(object : Callback<SquadInfoResponse> {
                    override fun onResponse(
                        call: Call<SquadInfoResponse>,
                        response: Response<SquadInfoResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { squadResponse ->
                                squadResponse.data?.let { squadInfo ->
                                    displaySquadInfo(squadInfo)
                                } ?: showError("No squad data found")
                            } ?: showError("Response body is null")
                        } else {
                            showError("API error: ${response.code()} - ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<SquadInfoResponse>, t: Throwable) {
                        showError("Network error: ${t.message}")
                    }
                })
            return
        }

        if (!mId.isNullOrBlank()) {
            Log.d("MatchSquadFragment", "Fetching match squad for matchId: $mId")
            CricApiClient.apiService.getSquadInfo(apiKey, mId)
                .enqueue(object : Callback<SquadInfoResponse> {
                    override fun onResponse(
                        call: Call<SquadInfoResponse>,
                        response: Response<SquadInfoResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { squadResponse ->
                                squadResponse.data?.let { squadInfo ->
                                    displaySquadInfo(squadInfo)
                                } ?: showError("No squad data found")
                            } ?: showError("Response body is null")
                        } else {
                            showError("API error: ${response.code()} - ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<SquadInfoResponse>, t: Throwable) {
                        showError("Network error: ${t.message}")
                    }
                })
        } else {
            showError("No series or match ID provided for squad fetch")
        }
    }

    private fun displaySquadInfo(squadInfo: SquadInfo) {
        // Update header with team names
        val teamNames = squadInfo.teamInfo?.joinToString(" vs ") { it.name ?: "Unknown" }
        tvSquadHeader.text = "$teamNames Squad"

        // Update adapter with players
        val allPlayers = squadInfo.players ?: emptyList()
        squadAdapter.updatePlayers(allPlayers)

        Log.d("MatchSquadFragment", "Displaying ${allPlayers.size} players")
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        Log.e("MatchSquadFragment", message)

        tvSquadHeader.text = "Squad Information"
        squadAdapter.updatePlayers(emptyList())
    }
}