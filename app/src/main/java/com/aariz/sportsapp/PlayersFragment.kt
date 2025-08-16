package com.aariz.sportsapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.adapters.PlayerAdapter
import com.aariz.sportsapp.api.CricApiClient
import com.aariz.sportsapp.databinding.FragmentPlayersBinding
import com.aariz.sportsapp.models.Player
import com.aariz.sportsapp.models.PlayerDetailResponse
import com.aariz.sportsapp.models.PlayerResponse
import com.aariz.sportsapp.PlayerDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlayerAdapter
    private val playersList = mutableListOf<Player>()

    private val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"

    // Pagination variables
    private var currentPage = 0
    private var isLoading = false
    private var lastQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupSearchBar()

        // Load initial players
        fetchPlayersFromApi(lastQuery, reset = true)

        return binding.root
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PlayerAdapter(playersList) { player ->
            val intent = Intent(requireContext(), PlayerDetailActivity::class.java)
            intent.putExtra("PLAYER_ID", player.id)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        // Scroll listener for pagination
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) { // Scrolling down
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    // Trigger loading before reaching end (buffer of 4 items)
                    if (!isLoading && (visibleItemCount + firstVisibleItemPosition >= totalItemCount - 4)) {
                        currentPage++
                        fetchPlayersFromApi(lastQuery, reset = false)
                    }
                }
            }
        })
    }

    private fun setupSearchBar() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.length >= 2 || query.isEmpty()) {
                    lastQuery = query
                    currentPage = 0
                    fetchPlayersFromApi(query, reset = true)
                }
            }
        })
    }

    private fun fetchPlayersFromApi(searchQuery: String, reset: Boolean) {
        if (isLoading) return
        isLoading = true

        if (reset) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.paginationProgress.visibility = View.VISIBLE
        }

        CricApiClient.apiService.getPlayers(apiKey, currentPage, searchQuery.ifBlank { null })
            .enqueue(object : Callback<PlayerResponse> {
                override fun onResponse(
                    call: Call<PlayerResponse>,
                    response: Response<PlayerResponse>
                ) {
                    binding.progressBar.visibility = View.GONE
                    binding.paginationProgress.visibility = View.GONE
                    isLoading = false

                    if (response.isSuccessful && response.body()?.data != null) {
                        if (reset) playersList.clear()

                        val newPlayers = response.body()!!.data
                        playersList.addAll(newPlayers)
                        adapter.notifyDataSetChanged()

                        // Fetch player images in background
                        for (player in newPlayers) {
                            fetchPlayerImage(player)
                        }
                    } else {
                        Toast.makeText(requireContext(), "No players found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    binding.paginationProgress.visibility = View.GONE
                    isLoading = false
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun fetchPlayerImage(player: Player) {
        CricApiClient.apiService.getPlayerInfo(apiKey, player.id)
            .enqueue(object : Callback<PlayerDetailResponse> {
                override fun onResponse(
                    call: Call<PlayerDetailResponse>,
                    response: Response<PlayerDetailResponse>
                ) {
                    if (response.isSuccessful && response.body()?.data != null) {
                        player.playerImg = response.body()!!.data?.playerImg
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<PlayerDetailResponse>, t: Throwable) {
                    // Ignore image fetch errors
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
