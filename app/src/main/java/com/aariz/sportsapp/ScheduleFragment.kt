package com.aariz.sportsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.adapters.ScheduleAdapter
import com.aariz.sportsapp.api.RetrofitInstance
import com.aariz.sportsapp.models.MatchItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val apiKey = "37abfde8-ac7d-4b87-98de-f602486ccb0b"

    private val pastMatches = mutableListOf<MatchItem>()
    private val liveMatches = mutableListOf<MatchItem>()
    private val upcomingMatches = mutableListOf<MatchItem>()

    private var currentLoaded = false
    private var upcomingLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        recyclerView = view.findViewById(R.id.rvSchedule)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        progressBar.visibility = View.VISIBLE
        fetchCurrentMatches()
        return view
    }

    private fun fetchCurrentMatches() {
        RetrofitInstance.api.getCurrentMatches(apiKey, 0)
            .enqueue(object : Callback<com.aariz.sportsapp.models.CurrentMatchesResponse> {
                override fun onResponse(
                    call: Call<com.aariz.sportsapp.models.CurrentMatchesResponse>,
                    response: Response<com.aariz.sportsapp.models.CurrentMatchesResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.data?.forEach {
                            Log.d("ScheduleFragment", "Current Match: ${it.name} | Status: ${it.status}")
                            when {
                                it.status?.contains("live", true) == true || it.status?.contains("in progress", true) == true -> {
                                    liveMatches.add(MatchItem(it.name ?: "", it.date, it.venue, it.status))
                                }
                                it.status?.contains("completed", true) == true || it.status?.contains("finished", true) == true -> {
                                    pastMatches.add(MatchItem(it.name ?: "", it.date, it.venue, it.status))
                                }
                            }
                        }
                    }
                    currentLoaded = true
                    fetchUpcomingMatches()
                }

                override fun onFailure(call: Call<com.aariz.sportsapp.models.CurrentMatchesResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    currentLoaded = true
                    fetchUpcomingMatches()
                }
            })
    }

    private fun fetchUpcomingMatches() {
        RetrofitInstance.api.getMatchesList(apiKey, 0)
            .enqueue(object : Callback<com.aariz.sportsapp.models.MatchesListResponse> {
                override fun onResponse(
                    call: Call<com.aariz.sportsapp.models.MatchesListResponse>,
                    response: Response<com.aariz.sportsapp.models.MatchesListResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.data?.forEach {
                            Log.d("ScheduleFragment", "Upcoming Match: ${it.name} | Status: ${it.status}")
                            if (it.status?.contains("upcoming", true) == true ||
                                it.status?.contains("scheduled", true) == true ||
                                it.status?.contains("match not started", true) == true
                            ) {
                                upcomingMatches.add(MatchItem(it.name ?: "", it.date, it.venue, it.status))
                            }
                        }
                    }
                    upcomingLoaded = true
                    mergeAndDisplayMatches()
                }

                override fun onFailure(call: Call<com.aariz.sportsapp.models.MatchesListResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    upcomingLoaded = true
                    mergeAndDisplayMatches()
                }
            })
    }

    private fun mergeAndDisplayMatches() {
        if (currentLoaded && upcomingLoaded) {
            val matchList = mutableListOf<MatchItem>()

            if (pastMatches.isNotEmpty()) {
                matchList.add(MatchItem("=== Past Matches ===", null, null, null))
                matchList.addAll(pastMatches)
            }
            if (liveMatches.isNotEmpty()) {
                matchList.add(MatchItem("=== Live Matches ===", null, null, null))
                matchList.addAll(liveMatches)
            }
            if (upcomingMatches.isNotEmpty()) {
                matchList.add(MatchItem("=== Upcoming Matches ===", null, null, null))
                matchList.addAll(upcomingMatches)
            }

            recyclerView.adapter = ScheduleAdapter(matchList)
            progressBar.visibility = View.GONE

            Log.d("ScheduleFragment", "Past: ${pastMatches.size}, Live: ${liveMatches.size}, Upcoming: ${upcomingMatches.size}")
        }
    }
}
