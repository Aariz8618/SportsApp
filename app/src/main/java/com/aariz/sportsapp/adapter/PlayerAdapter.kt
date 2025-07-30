package com.aariz.sportsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.databinding.ItemPlayerBinding
import com.aariz.sportsapp.model.Player
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class PlayerAdapter(
    private val onPlayerClick: (Player) -> Unit = {}
) : ListAdapter<Player, PlayerAdapter.PlayerViewHolder>(PlayerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PlayerViewHolder(
        private val binding: ItemPlayerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: Player) {
            binding.apply {
                // Player name
                tvPlayerName.text = player.fullName.ifEmpty { "${player.firstName} ${player.lastName}" }
                
                // Country
                tvPlayerCountry.text = player.country?.name ?: "Unknown"
                
                // Position
                tvPlayerPosition.text = player.position?.name ?: "Unknown Position"
                
                // Batting and bowling style
                val battingStyle = player.battingStyle?.takeIf { it.isNotBlank() } ?: "N/A"
                val bowlingStyle = player.bowlingStyle?.takeIf { it.isNotBlank() } ?: "N/A"
                tvPlayerStyle.text = "$battingStyle • $bowlingStyle"
                
                // Player image
                if (!player.imagePath.isNullOrBlank()) {
                    Glide.with(binding.root.context)
                        .load(player.imagePath)
                        .transform(CircleCrop())
                        .placeholder(com.aariz.sportsapp.R.drawable.ic_team_placeholder)
                        .error(com.aariz.sportsapp.R.drawable.ic_team_placeholder)
                        .into(ivPlayerImage)
                } else {
                    // Set default placeholder image
                    ivPlayerImage.setImageResource(com.aariz.sportsapp.R.drawable.ic_team_placeholder)
                }
                
                // Career stats (if available)
                player.career?.firstOrNull()?.let { career ->
                    val battingStats = career.batting
                    val bowlingStats = career.bowling
                    
                    val statsText = buildString {
                        battingStats?.let { batting ->
                            if (batting.runsScored != null && batting.average != null) {
                                append("Runs: ${batting.runsScored} • Avg: ${"%.2f".format(batting.average)}")
                            }
                        }
                        
                        bowlingStats?.let { bowling ->
                            if (isNotEmpty()) append(" • ")
                            if (bowling.wickets != null && bowling.average != null) {
                                append("Wickets: ${bowling.wickets} • Avg: ${"%.2f".format(bowling.average)}")
                            }
                        }
                    }
                    
                    tvPlayerStats.text = statsText.ifEmpty { "No stats available" }
                } ?: run {
                    tvPlayerStats.text = "No stats available"
                }
                
                // Click listener
                root.setOnClickListener {
                    onPlayerClick(player)
                }
            }
        }
    }

    class PlayerDiffCallback : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }
    }
}
