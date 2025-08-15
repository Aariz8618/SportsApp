package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.MatchItem

class ScheduleAdapter(
    private val matchList: List<MatchItem>,
    private val onMatchClick: ((MatchItem) -> Unit)? = null
) : RecyclerView.Adapter<ScheduleAdapter.MatchViewHolder>() {

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val matchName: TextView = itemView.findViewById(R.id.tvMatchName)
        val matchDate: TextView = itemView.findViewById(R.id.tvMatchDate)
        val matchVenue: TextView = itemView.findViewById(R.id.tvMatchVenue)
        val matchStatus: TextView = itemView.findViewById(R.id.tvMatchStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matchList[position]

        holder.matchName.text = match.name
        holder.matchDate.text = match.date ?: "Date: N/A"
        holder.matchVenue.text = match.venue ?: "Venue: N/A"
        holder.matchStatus.text = match.status ?: "Status: N/A"

        // Set click listener only for actual matches (not section headers)
        if (match.id != null && !match.name.startsWith("===")) {
            holder.itemView.setOnClickListener {
                onMatchClick?.invoke(match)
            }
            // Add visual feedback for clickable items
            holder.itemView.isClickable = true
            holder.itemView.isFocusable = true
            // You can add a ripple effect or change background here
        } else {
            holder.itemView.setOnClickListener(null)
            holder.itemView.isClickable = false
            holder.itemView.isFocusable = false

            // Style section headers differently
            if (match.name.startsWith("===")) {
                holder.matchName.textSize = 18f
                holder.matchName.setTextColor(holder.itemView.context.getColor(R.color.primary_color))
                // Hide other fields for section headers
                holder.matchDate.visibility = View.GONE
                holder.matchVenue.visibility = View.GONE
                holder.matchStatus.visibility = View.GONE
            } else {
                // Reset styling for regular items
                holder.matchName.textSize = 16f
                holder.matchDate.visibility = View.VISIBLE
                holder.matchVenue.visibility = View.VISIBLE
                holder.matchStatus.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = matchList.size
}