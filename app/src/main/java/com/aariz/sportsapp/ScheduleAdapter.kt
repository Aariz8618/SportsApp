package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.MatchItem
import android.util.Log

class ScheduleAdapter(
    private val matchList: List<MatchItem>,
    private val onMatchClick: ((MatchItem) -> Unit)? = null
) : RecyclerView.Adapter<ScheduleAdapter.MatchViewHolder>() {

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val matchName: TextView = itemView.findViewById(R.id.tvMatchName)
        val matchDate: TextView = itemView.findViewById(R.id.tvMatchDate)
        val matchVenue: TextView = itemView.findViewById(R.id.tvMatchVenue)
        val matchStatus: TextView = itemView.findViewById(R.id.tvMatchStatus)
        val matchStatus2: TextView = itemView.findViewById(R.id.tvMatchStatus2)
        val matchType: TextView = itemView.findViewById(R.id.tvMatchtype)
        val matchCount: TextView = itemView.findViewById(R.id.tvMatchCount)

        init {
            // Initialize any view-related setup here if needed
            // Don't hide matchStatus2 by default - let the binding logic decide
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule_match, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        try {
            val match = matchList.getOrNull(position) ?: return

            // Set match name and count
            holder.matchName.text = match.name ?: "Match Name N/A"
            holder.matchCount.text = if (!match.matchCount.isNullOrEmpty()) "Match ${match.matchCount}" else ""

            // Set match type in uppercase
            holder.matchType.text = (match.matchType ?: "T20").uppercase()

            // Set other views with null safety
            holder.matchDate.text = match.date ?: "Date: N/A"
            holder.matchVenue.text = match.venue ?: "Venue: N/A"

            // Handle match status with null safety
            val originalStatus = match.status ?: ""
            val statusLower = originalStatus.lowercase()
            when {
                statusLower.contains("live") || statusLower.contains("in progress") -> {
                    setStatus(holder, "LIVE", R.drawable.live_status_background, match.result ?: "In Progress")
                }
                statusLower.contains("completed") || statusLower.contains("finished") -> {
                    setStatus(holder, "COMPLETED", R.drawable.completed_status_background, match.result ?: "Match Ended")
                }
                statusLower.contains("upcoming") || statusLower.contains("scheduled") -> {
                    setStatus(holder, "UPCOMING", R.drawable.upcoming_status_background, "Match starts at ${match.date ?: "TBD"}")
                }
                else -> {
                    holder.matchStatus2.visibility = View.GONE
                    // Display original status with proper capitalization
                    val displayStatus = if (originalStatus.isNotEmpty()) {
                        originalStatus.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                    } else {
                        "Status: N/A"
                    }
                    holder.matchStatus.text = displayStatus
                }
            }

            // Set click listener only for actual matches (not section headers)
            if (match.id != null && !match.name.isNullOrEmpty() && !match.name.startsWith("===")) {
                holder.itemView.setOnClickListener {
                    onMatchClick?.invoke(match)
                }
                holder.itemView.isClickable = true
                holder.itemView.isFocusable = true
            } else {
                holder.itemView.setOnClickListener(null)
                holder.itemView.isClickable = false
                holder.itemView.isFocusable = false

                if (match.name?.startsWith("===") == true) {
                    // Handle section header
                    holder.matchName.textSize = 18f
                    try {
                        holder.matchName.setTextColor(holder.itemView.context.getColor(R.color.primary_color))
                    } catch (e: Exception) {
                        holder.matchName.setTextColor(holder.itemView.context.resources.getColor(android.R.color.black))
                    }
                    holder.matchDate.visibility = View.GONE
                    holder.matchVenue.visibility = View.GONE
                    holder.matchStatus.visibility = View.GONE
                    holder.matchStatus2.visibility = View.GONE
                    holder.matchType.visibility = View.GONE
                    holder.matchCount.visibility = View.GONE
                } else {
                    // Reset styles for regular items
                    holder.matchName.textSize = 16f
                    holder.matchDate.visibility = View.VISIBLE
                    holder.matchVenue.visibility = View.VISIBLE
                    holder.matchStatus.visibility = View.VISIBLE
                    holder.matchType.visibility = View.VISIBLE
                    holder.matchCount.visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            Log.e("ScheduleAdapter", "Error binding match at position $position: ${e.message}")
            // Set default values to prevent crash
            holder.matchName.text = "Error loading match"
            holder.matchStatus2.visibility = View.GONE
            holder.matchStatus.text = ""
            holder.itemView.isClickable = false
        }
    }

    private fun setStatus(holder: MatchViewHolder, statusText: String, @DrawableRes backgroundRes: Int, statusMessage: String) {
        try {
            Log.d("ScheduleAdapter", "Setting status badge: '$statusText' with background: $backgroundRes")
            holder.matchStatus2.text = statusText
            holder.matchStatus2.visibility = View.VISIBLE

            // Try to set the background resource, fallback to color if it fails
            try {
                holder.matchStatus2.setBackgroundResource(backgroundRes)
            } catch (e: Exception) {
                Log.w("ScheduleAdapter", "Background resource not found, using color fallback")
                // Fallback colors based on status
                val color = when (statusText.uppercase()) {
                    "LIVE" -> android.graphics.Color.parseColor("#FF4444")
                    "COMPLETED" -> android.graphics.Color.parseColor("#4CAF50")
                    "UPCOMING" -> android.graphics.Color.parseColor("#2196F3")
                    else -> android.graphics.Color.parseColor("#757575")
                }
                holder.matchStatus2.setBackgroundColor(color)
            }

            holder.matchStatus.text = statusMessage
            Log.d("ScheduleAdapter", "Status badge set successfully")
        } catch (e: Exception) {
            Log.e("ScheduleAdapter", "Error setting status: ${e.message}")
            // Fallback - still try to show something
            holder.matchStatus2.text = statusText
            holder.matchStatus2.visibility = View.VISIBLE
            holder.matchStatus2.setBackgroundColor(android.graphics.Color.GRAY)
            holder.matchStatus.text = statusMessage
        }
    }

    override fun getItemCount(): Int = matchList.size
}