package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.MatchItem

class ScheduleAdapter(private val matchList: List<MatchItem>) :
    RecyclerView.Adapter<ScheduleAdapter.MatchViewHolder>() {

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
    }

    override fun getItemCount(): Int = matchList.size
}
