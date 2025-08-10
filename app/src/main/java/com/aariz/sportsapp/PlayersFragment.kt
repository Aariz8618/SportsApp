package com.aariz.sportsapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.adapters.PlayerAdapter
import com.aariz.sportsapp.api.RetrofitInstance
import com.aariz.sportsapp.models.Player
import com.aariz.sportsapp.models.PlayerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlayerAdapter
    private lateinit var etSearchPlayer: EditText

    private val API_KEY = "37abfde8-ac7d-4b87-98de-f602486ccb0b"

    private var allPlayers = mutableListOf<Player>()
    private var filteredPlayers = mutableListOf<Player>()
    private var offset = 0
    private var isLoading = false
    private var allPlayersLoaded = false
    private val PAGE_SIZE = 25

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_players, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewPlayers)
        etSearchPlayer = view.findViewById(R.id.etSearchPlayer)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = PlayerAdapter(filteredPlayers)
        recyclerView.adapter = adapter

        setupSearch()
        setupScrollListener()
        fetchPlayers()

        return view
    }

    private fun setupSearch() {
        etSearchPlayer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim().lowercase()
                filteredPlayers.clear()
                if (query.isEmpty()) {
                    filteredPlayers.addAll(allPlayers)
                } else {
                    filteredPlayers.addAll(allPlayers.filter {
                        it.name.lowercase().contains(query)
                    })
                }
                adapter.updateData(filteredPlayers)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)
                val layoutManager = rv.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && !allPlayersLoaded && lastVisibleItem >= totalItemCount - 4) {
                    fetchPlayers()
                }
            }
        })
    }

    private fun fetchPlayers() {
        isLoading = true
        RetrofitInstance.api.getPlayers(API_KEY, offset)
            .enqueue(object : Callback<PlayerResponse> {
                override fun onResponse(
                    call: Call<PlayerResponse>,
                    response: Response<PlayerResponse>
                ) {
                    isLoading = false
                    if (response.isSuccessful) {
                        val newPlayers = response.body()?.data ?: emptyList()

                        if (newPlayers.isEmpty()) {
                            allPlayersLoaded = true
                            return
                        }

                        allPlayers.addAll(newPlayers)
                        filteredPlayers.clear()
                        filteredPlayers.addAll(allPlayers)
                        adapter.updateData(filteredPlayers)

                        offset += PAGE_SIZE
                    } else {
                        Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                    isLoading = false
                    Toast.makeText(requireContext(), "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
