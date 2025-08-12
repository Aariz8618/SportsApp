package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.Player
import com.bumptech.glide.Glide

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
        holder.tvName.text = player.name
        holder.tvCountry.text = player.country

        Glide.with(holder.itemView.context)
            .load(player.playerImg)
            .placeholder(R.drawable.ic_player_placeholder)
            .error(R.drawable.ic_player_placeholder)
            .into(holder.ivImage)

        holder.itemView.setOnClickListener {
            onPlayerClick?.invoke(player)
        }
    }

    override fun getItemCount(): Int = playerList.size

    fun updateData(newList: List<Player>) {
        playerList = newList
        notifyDataSetChanged()
    }
}
