package com.aariz.sportsapp.ui

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
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchScorecardFragment : Fragment() {

    private lateinit var tabNavigation: TabLayout
    private lateinit var tvSeriesTitle: TextView
    private lateinit var tvMeta: TextView
    private lateinit var rvInnings: RecyclerView
    private lateinit var progressLoading: ProgressBar

    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"
    private var matchId: String? = null
    private var matchName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            matchId = it.getString("match_id")
            matchName = it.getString("match_name")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_match_scorecard, container, false)
        tabNavigation = view.findViewById(R.id.tab_navigation)
        tvSeriesTitle = view.findViewById(R.id.tv_series_title)
        tvMeta = view.findViewById(R.id.tv_meta)
        rvInnings = view.findViewById(R.id.rv_innings)
        progressLoading = view.findViewById(R.id.progress_loading)

        // Select Scorecard tab
        tabNavigation.getTabAt(1)?.select()
        tabNavigation.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    // Navigate back to Summary
                    val frag = MatchDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString("match_id", matchId)
                            putString("match_name", matchName)
                        }
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, frag)
                        .addToBackStack(null)
                        .commit()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        rvInnings.layoutManager = LinearLayoutManager(requireContext())
        rvInnings.adapter = com.aariz.sportsapp.adapters.InningsAdapter(emptyList())

        fetchScorecard()
        return view
    }

    private fun setLoading(visible: Boolean) {
        progressLoading.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun fetchScorecard() {
        val id = matchId ?: return
        setLoading(true)
        CricApiClient.apiService.getScorecard(apiKey, id).enqueue(object : Callback<ScorecardResponse> {
            override fun onResponse(
                call: Call<ScorecardResponse>,
                response: Response<ScorecardResponse>
            ) {
                setLoading(false)
                if (!response.isSuccessful) {
                    showError("API error: ${response.code()} - ${response.message()}")
                    return
                }
                val body = response.body()
                val data = body?.data
                if (data == null) {
                    showError("No scorecard data")
                    return
                }
                bindHeader(data)
                val innings = data.innings ?: emptyList()
                (rvInnings.adapter as? com.aariz.sportsapp.adapters.InningsAdapter)?.update(innings)
            }

            override fun onFailure(call: Call<ScorecardResponse>, t: Throwable) {
                setLoading(false)
                showError("Network error: ${t.message}")
            }
        })
    }

    private fun bindHeader(data: ScorecardData) {
        // Title: either matchName or combine team short names
        val title = matchName ?: run {
            val names = data.teamInfo?.mapNotNull { it.shortName ?: it.name } ?: data.teams ?: emptyList()
            if (names.size >= 2) "${names[0]} vs ${names[1]}" else names.joinToString(" vs ")
        }
        tvSeriesTitle.text = title
        val metaParts = mutableListOf<String>()
        data.venue?.let { metaParts.add(it) }
        data.dateTimeGMT?.let { metaParts.add(it) }
        data.matchType?.let { metaParts.add(it) }
        tvMeta.text = metaParts.joinToString("  •  ")
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        Log.e("MatchScorecardFragment", message)
    }
}
