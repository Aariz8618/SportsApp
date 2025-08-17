package com.aariz.sportsapp

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
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.models.MatchItem
import com.aariz.sportsapp.models.CurrentMatchesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"

    private val currentMatches = mutableListOf<MatchItem>()

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
        Log.d("ScheduleFragment", "Starting API call to fetch current matches...")

        CricApiClient.apiService.getCurrentMatches(apiKey, 0)
            .enqueue(object : Callback<CurrentMatchesResponse> {
                override fun onResponse(
                    call: Call<CurrentMatchesResponse>,
                    response: Response<CurrentMatchesResponse>
                ) {
                    if (!isAdded) return
                    progressBar.visibility = View.GONE
                    Log.d("ScheduleFragment", "API Response received. Success: ${response.isSuccessful}, Code: ${response.code()}")

                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!
                        Log.d("ScheduleFragment", "Response body status: ${responseBody.status}")
                        Log.d("ScheduleFragment", "Response body data size: ${responseBody.data?.size}")
                        Log.d("ScheduleFragment", "Full response: $responseBody")

                        currentMatches.clear()

                        responseBody.data?.let { matchDataList ->
                            Log.d("ScheduleFragment", "Processing ${matchDataList.size} matches from API...")

                            matchDataList.forEachIndexed { index, match ->
                                Log.d("ScheduleFragment", "Match $index: Name=${match.name}, Status=${match.status}, Date=${match.date}, ID=${match.id}")

                                // TEMPORARILY SHOW ALL MATCHES (regardless of status) to debug what's available
                                val matchItem = MatchItem(
                                    name = match.name ?: "Unknown Match",
                                    date = match.date,
                                    venue = match.venue,
                                    status = match.status,
                                    id = match.id
                                )
                                currentMatches.add(matchItem)
                                Log.d("ScheduleFragment", "Added match: ${matchItem.name}")
                            }
                        } ?: run {
                            Log.w("ScheduleFragment", "No data array in response")
                        }

                        Log.d("ScheduleFragment", "Total matches loaded: ${currentMatches.size}")
                        displayCurrentMatches()
                    } else {
                        Log.e("ScheduleFragment", "API response not successful or body is null. Response code: ${response.code()}")
                        Log.e("ScheduleFragment", "Response message: ${response.message()}")
                        Log.e("ScheduleFragment", "Response error body: ${response.errorBody()?.string()}")
                        context?.let { ctx ->
                            Toast.makeText(ctx, "Failed to load matches (Code: ${response.code()})", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<CurrentMatchesResponse>, t: Throwable) {
                    if (!isAdded) return
                    progressBar.visibility = View.GONE
                    Log.e("ScheduleFragment", "API call failed: ${t.message}")
                    Log.e("ScheduleFragment", "Exception: ", t)
                    context?.let { ctx ->
                        Toast.makeText(ctx, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    private fun displayCurrentMatches() {
        Log.d("ScheduleFragment", "Displaying matches. Count: ${currentMatches.size}")

        val matchList = mutableListOf<MatchItem>()

        if (currentMatches.isNotEmpty()) {
            matchList.add(MatchItem("=== All Matches (Debug Mode) ===", null, null, null, null))
            matchList.addAll(currentMatches)
            Log.d("ScheduleFragment", "Added ${currentMatches.size} matches to display list")
        } else {
            matchList.add(MatchItem("No matches returned from API", null, null, null, null))
            Log.w("ScheduleFragment", "No matches to display")
        }

        recyclerView.adapter = ScheduleAdapter(matchList) { matchItem ->
            Log.d("ScheduleFragment", "Match clicked: ${matchItem.name}, ID: ${matchItem.id}")
            matchItem.id?.let { matchId ->
                navigateToMatchDetails(matchId, matchItem.name)
            } ?: run {
                Log.w("ScheduleFragment", "No match ID available for clicked match")
                context?.let { ctx ->
                    Toast.makeText(ctx, "No match ID available", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Log.d("ScheduleFragment", "RecyclerView adapter set with ${matchList.size} total items")
    }

    private fun navigateToMatchDetails(matchId: String, matchName: String?) {
        Log.d("ScheduleFragment", "Navigating to match details. ID: $matchId, Name: $matchName")
        val matchDetailFragment = MatchDetailFragment().apply {
            arguments = Bundle().apply {
                putString("match_id", matchId)
                putString("match_name", matchName ?: "Match Details")
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, matchDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}