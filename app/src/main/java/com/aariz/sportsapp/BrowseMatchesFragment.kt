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
import com.aariz.sportsapp.adapters.BrowseMatchesAdapter
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.models.MatchItem
import com.aariz.sportsapp.models.MatchesListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrowseMatchesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"
    private val allMatches = mutableListOf<MatchItem>()

    private var matchesLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_browse_matches, container, false)

        recyclerView = view.findViewById(R.id.rvBrowseMatches)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        progressBar.visibility = View.VISIBLE
        fetchAllMatches()
        return view
    }

    private fun fetchAllMatches() {
        CricApiClient.apiService.getMatchesList(apiKey, 0)
            .enqueue(object : Callback<MatchesListResponse> {
                override fun onResponse(
                    call: Call<MatchesListResponse>,
                    response: Response<MatchesListResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.data?.forEach { match ->
                            Log.d("BrowseMatchesFragment", "Match: ${match.name} | Status: ${match.status}")
                            allMatches.add(MatchItem(
                                name = match.name ?: "Unknown Match",
                                date = match.date,
                                venue = match.venue,
                                status = match.status,
                                id = match.id
                            ))
                        }
                        displayMatches()
                    } else {
                        Log.e("BrowseMatchesFragment", "API error: ${response.code()}")
                        showError("Failed to fetch matches. Please try again.")
                    }
                    matchesLoaded = true
                    progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<MatchesListResponse>, t: Throwable) {
                    Log.e("BrowseMatchesFragment", "API failure: ${t.message}")
                    showError("Network error: ${t.message}")
                    matchesLoaded = true
                    progressBar.visibility = View.GONE
                }
            })
    }

    private fun displayMatches() {
        if (allMatches.isEmpty()) {
            allMatches.add(MatchItem("No matches available", null, null, "Please check back later", null))
        }

        recyclerView.adapter = BrowseMatchesAdapter(allMatches) { matchItem ->
            // Handle match click - navigate to match details
            matchItem.id?.let { matchId ->
                navigateToMatchDetails(matchId, matchItem.name)
            } ?: run {
                Toast.makeText(requireContext(), "Match details not available", Toast.LENGTH_SHORT).show()
            }
        }

        Log.d("BrowseMatchesFragment", "Displaying ${allMatches.size} matches")
    }

    private fun navigateToMatchDetails(matchId: String, matchName: String?) {
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

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        // Add a message to the list indicating the error
        allMatches.clear()
        allMatches.add(MatchItem("Error loading matches", null, null, message, null))
        displayMatches()
    }
}