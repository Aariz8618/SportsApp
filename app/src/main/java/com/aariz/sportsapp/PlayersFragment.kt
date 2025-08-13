package com.aariz.sportsapp.ui.players

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.aariz.sportsapp.ui.PlayerDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlayerAdapter
    private val playersList = mutableListOf<Player>()

    private val apiKey = "37abfde8-ac7d-4b87-98de-f602486ccb0b"

    // Pagination variables
    private var currentPage = 0
    private var isLoading = false
    private var lastQuery = ""

    companion object {
        private const val TAG = "PlayersFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)

        Log.d(TAG, "PlayersFragment created")

        setupRecyclerView()
        setupSearchBar()

        // Check network connectivity first
        if (isNetworkAvailable()) {
            Log.d(TAG, "Network available, loading players")
            fetchPlayersFromApi(lastQuery, reset = true)
        } else {
            Log.e(TAG, "No network connection available")
            showError("No internet connection available")
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PlayerAdapter(playersList) { player ->
            Log.d(TAG, "Player clicked: ${player.name} (ID: ${player.id})")
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
                        Log.d(TAG, "Loading next page: ${currentPage + 1}")
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
                    Log.d(TAG, "Search query changed: '$query'")
                    lastQuery = query
                    currentPage = 0
                    fetchPlayersFromApi(query, reset = true)
                }
            }
        })
    }

    private fun fetchPlayersFromApi(searchQuery: String, reset: Boolean) {
        if (isLoading) {
            Log.d(TAG, "Already loading, skipping request")
            return
        }

        Log.d(TAG, "Fetching players - Query: '$searchQuery', Page: $currentPage, Reset: $reset")

        if (!isNetworkAvailable()) {
            Log.e(TAG, "Network not available")
            showError("No internet connection")
            return
        }

        isLoading = true

        if (reset) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.paginationProgress.visibility = View.VISIBLE
        }

        Log.d(TAG, "Making API call with apiKey: ${apiKey.take(8)}..., offset: $currentPage")

        CricApiClient.apiService.getPlayers(apiKey, currentPage, searchQuery.ifBlank { null })
            .enqueue(object : Callback<PlayerResponse> {
                override fun onResponse(
                    call: Call<PlayerResponse>,
                    response: Response<PlayerResponse>
                ) {
                    Log.d(TAG, "API Response received - Code: ${response.code()}, Success: ${response.isSuccessful}")

                    binding.progressBar.visibility = View.GONE
                    binding.paginationProgress.visibility = View.GONE
                    isLoading = false

                    if (response.isSuccessful) {
                        val body = response.body()
                        Log.d(TAG, "Response body: $body")

                        if (body?.data != null) {
                            if (reset) playersList.clear()

                            val newPlayers = body.data
                            Log.d(TAG, "Received ${newPlayers.size} players")

                            playersList.addAll(newPlayers)
                            adapter.notifyDataSetChanged()

                            // Fetch player images in background
                            for (player in newPlayers) {
                                fetchPlayerImage(player)
                            }
                        } else {
                            Log.w(TAG, "Response body or data is null")
                            showError("No players found")
                        }
                    } else {
                        Log.e(TAG, "API call failed with code: ${response.code()}")
                        val errorBody = response.errorBody()?.string()
                        Log.e(TAG, "Error body: $errorBody")
                        showError("Failed to load players (${response.code()})")
                    }
                }

                override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                    Log.e(TAG, "API call failed", t)

                    binding.progressBar.visibility = View.GONE
                    binding.paginationProgress.visibility = View.GONE
                    isLoading = false

                    val errorMessage = when (t) {
                        is UnknownHostException -> {
                            Log.e(TAG, "DNS resolution failed for: ${t.message}")
                            "Unable to connect to server. Please check your internet connection."
                        }
                        is SocketTimeoutException -> {
                            Log.e(TAG, "Request timeout")
                            "Request timeout. Please try again."
                        }
                        is IOException -> {
                            Log.e(TAG, "Network IO error: ${t.message}")
                            "Network error. Please check your connection."
                        }
                        else -> {
                            Log.e(TAG, "Unknown error: ${t.message}")
                            "Error: ${t.message}"
                        }
                    }

                    showError(errorMessage)
                }
            })
    }

    private fun fetchPlayerImage(player: Player) {
        Log.d(TAG, "Fetching image for player: ${player.name}")

        CricApiClient.apiService.getPlayerInfo(apiKey, player.id)
            .enqueue(object : Callback<PlayerDetailResponse> {
                override fun onResponse(
                    call: Call<PlayerDetailResponse>,
                    response: Response<PlayerDetailResponse>
                ) {
                    if (response.isSuccessful && response.body()?.data != null) {
                        val imageUrl = response.body()!!.data.playerImg
                        Log.d(TAG, "Got image URL for ${player.name}: $imageUrl")
                        player.playerImg = imageUrl
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.w(TAG, "Failed to get image for ${player.name}")
                    }
                }

                override fun onFailure(call: Call<PlayerDetailResponse>, t: Throwable) {
                    Log.w(TAG, "Failed to fetch image for ${player.name}: ${t.message}")
                }
            })
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            val hasInternet = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            Log.d(TAG, "Network available: $hasInternet")
            hasInternet
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            val isConnected = networkInfo?.isConnected == true
            Log.d(TAG, "Network available (legacy): $isConnected")
            isConnected
        }
    }

    private fun showError(message: String) {
        Log.e(TAG, "Showing error: $message")
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "PlayersFragment destroyed")
        _binding = null
    }
}