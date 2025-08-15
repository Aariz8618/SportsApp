package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.MatchItem

class BrowseMatchesAdapter(
    private val matchList: List<MatchItem>,
    private val onMatchClick: (MatchItem) -> Unit
) : RecyclerView.Adapter<BrowseMatchesAdapter.MatchViewHolder>() {

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val matchName: TextView = itemView.findViewById(R.id.tvMatchName)
        val matchDate: TextView = itemView.findViewById(R.id.tvMatchDate)
        val matchVenue: TextView = itemView.findViewById(R.id.tvMatchVenue)
        val matchStatus: TextView = itemView.findViewById(R.id.tvMatchStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_browse_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matchList[position]

        holder.matchName.text = match.name
        holder.matchDate.text = match.date ?: "Date: N/A"
        holder.matchVenue.text = match.venue ?: "Venue: N/A"
        holder.matchStatus.text = match.status ?: "Status: N/A"

        // Set click listener for matches that have an ID
        if (match.id != null && match.name != "No matches available" && match.name != "Error loading matches") {
            holder.itemView.setOnClickListener {
                onMatchClick(match)
            }
            holder.itemView.isClickable = true
            holder.itemView.isFocusable = true

            // Add visual feedback for clickable items
            holder.itemView.alpha = 1.0f
        } else {
            holder.itemView.setOnClickListener(null)
            holder.itemView.isClickable = false
            holder.itemView.isFocusable = false

            // Slightly dim non-clickable items
            holder.itemView.alpha = 0.7f
        }

        // Color code by status
        when {
            match.status?.contains("live", true) == true ||
                    match.status?.contains("in progress", true) == true -> {
                holder.matchStatus.setTextColor(holder.itemView.context.getColor(android.R.color.holo_red_dark))
            }
            match.status?.contains("completed", true) == true ||
                    match.status?.contains("finished", true) == true -> {
                holder.matchStatus.setTextColor(holder.itemView.context.getColor(android.R.color.holo_green_dark))
            }
            match.status?.contains("upcoming", true) == true ||
                    match.status?.contains("scheduled", true) == true -> {
                holder.matchStatus.setTextColor(holder.itemView.context.getColor(android.R.color.holo_blue_dark))
            }
            else -> {
                holder.matchStatus.setTextColor(holder.itemView.context.getColor(android.R.color.darker_gray))
            }
        }
    }

    override fun getItemCount(): Int = matchList.size
}