package com.aariz.sportsapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.Player
import com.aariz.sportsapp.PlayerDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PlayerAdapter(
    private var playerList: List<Player>,
    private val onPlayerClick: ((Player) -> Unit)? = null
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImage: ImageView = itemView.findViewById(R.id.ivPlayerImage)
        val tvName: TextView = itemView.findViewById(R.id.tvPlayerName)
        val tvCountry: TextView = itemView.findViewById(R.id.tvPlayerCountry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = playerList[position]

        // Populate player data
        holder.tvName.text = player.name ?: "Unknown Player"
        holder.tvCountry.text = player.country ?: "Unknown Country"

        // Load image with better caching and error handling
        val imageUrl = player.playerImg
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_player_placeholder)
                .error(R.drawable.ic_player_placeholder)
                .centerCrop()
                .into(holder.ivImage)
        } else {
            holder.ivImage.setImageResource(R.drawable.ic_player_placeholder)
        }

        // Handle click events
        holder.itemView.setOnClickListener {
            // Use custom click listener if provided
            if (onPlayerClick != null) {
                onPlayerClick.invoke(player)
            } else {
                // Default behavior - navigate to PlayerDetailActivity
                navigateToPlayerDetail(holder.itemView, player)
            }
        }
    }

    private fun navigateToPlayerDetail(view: View, player: Player) {
        val context = view.context
        val intent = Intent(context, PlayerDetailActivity::class.java).apply {
            // Pass player ID - make sure to use the correct key name
            putExtra("PLAYER_ID", player.id)
            // You can also pass additional data if needed
            putExtra("PLAYER_NAME", player.name)
        }
        context.startActivity(intent)
    }

    override fun getItemCount(): Int = playerList.size

    fun updateData(newList: List<Player>) {
        playerList = newList
        notifyDataSetChanged()
    }

    // Optional: More efficient update method using DiffUtil
    fun updateDataWithDiff(newList: List<Player>) {
        // You can implement DiffUtil here for better performance
        // For now, using the simple approach
        updateData(newList)
    }
}