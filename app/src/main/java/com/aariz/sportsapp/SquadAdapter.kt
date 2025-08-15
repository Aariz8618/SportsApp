package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.PlayerInfo

class SquadAdapter(private var players: List<PlayerInfo>) :
    RecyclerView.Adapter<SquadAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerImage: ImageView = itemView.findViewById(R.id.ivPlayerImage)
        val playerName: TextView = itemView.findViewById(R.id.tvPlayerName)
        val playerCountry: TextView = itemView.findViewById(R.id.tvPlayerCountry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]

        holder.playerName.text = player.name ?: "Unknown Player"
        holder.playerCountry.text = player.country ?: "Unknown Country"

        // Load player image if available
        // You can use Glide or Picasso here to load the image from player.playerImg
        // For now, it will show the placeholder image defined in the layout
    }

    override fun getItemCount(): Int = players.size

    fun updatePlayers(newPlayers: List<PlayerInfo>) {
        players = newPlayers
        notifyDataSetChanged()
    }
}