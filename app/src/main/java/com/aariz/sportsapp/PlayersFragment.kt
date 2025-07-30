package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.adapter.PlayerAdapter
import android.text.Editable
import android.text.TextWatcher
import com.aariz.sportsapp.databinding.FragmentPlayersBinding
import com.aariz.sportsapp.viewmodel.PlayerViewModel
import com.aariz.sportsapp.viewmodel.PlayerViewModelFactory

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PlayerViewModel
    private lateinit var playerAdapter: PlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
        setupObservers()
        setupListeners()

        // Initial fetch of players
        viewModel.fetchPlayers()
    }

    private fun setupRecyclerView() {
        playerAdapter = PlayerAdapter { player ->
            // Navigate to player detail screen
            val fragment = PlayerDetailFragment.newInstance(player)
            
            // Update header title in MainActivity
            (activity as? MainActivity)?.updateHeaderTitle(player.fullName.ifEmpty { "${player.firstName} ${player.lastName}" })
            
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerViewPlayers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playerAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (viewModel.isLoading.value != true && 
                        viewModel.isLoadingMore.value != true &&
                        visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                        firstVisibleItemPosition >= 0) {
                        viewModel.loadMorePlayers()
                    }
                }
            })
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, PlayerViewModelFactory()).get(PlayerViewModel::class.java)
    }

    private fun setupObservers() {
        // Observe original players list
        viewModel.players.observe(viewLifecycleOwner) { players ->
            if (binding.etSearchPlayers.text.isNullOrEmpty()) {
                playerAdapter.submitList(players)
            }
            // Update filtered players when original list changes
            viewModel.searchPlayers(binding.etSearchPlayers.text.toString())
        }
        
        // Observe filtered players list
        viewModel.filteredPlayers.observe(viewLifecycleOwner) { filteredPlayers ->
            playerAdapter.submitList(filteredPlayers)
            // Show/hide empty state
            if (filteredPlayers.isEmpty() && viewModel.isLoading.value != true) {
                binding.tvError.text = if (binding.etSearchPlayers.text.isNullOrEmpty()) {
                    "No players found"
                } else {
                    "No players match your search"
                }
                binding.tvError.visibility = View.VISIBLE
            } else if (filteredPlayers.isNotEmpty()) {
                binding.tvError.visibility = View.GONE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            // Hide empty state when loading
            if (isLoading) {
                binding.tvError.visibility = View.GONE
            }
        }
        
        viewModel.isLoadingMore.observe(viewLifecycleOwner) { isLoadingMore ->
            binding.progressBarLoadMore.visibility = if (isLoadingMore) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                binding.tvError.text = error
                binding.tvError.visibility = View.VISIBLE
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }
    }
    
    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshPlayers()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        
        // Implement search functionality
        binding.etSearchPlayers.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchPlayers(s.toString())
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
