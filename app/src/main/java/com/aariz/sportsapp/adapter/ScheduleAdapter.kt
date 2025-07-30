package com.aariz.sportsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.model.ScheduleMatch

class ScheduleAdapter : ListAdapter<ScheduleMatch, ScheduleAdapter.ScheduleViewHolder>(ScheduleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule_match, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvSeries: TextView = itemView.findViewById(R.id.tvSeries)
        private val tvMatchDesc: TextView = itemView.findViewById(R.id.tvMatchDesc)
        private val tvTeams: TextView = itemView.findViewById(R.id.tvTeams)
        private val tvVenue: TextView = itemView.findViewById(R.id.tvVenue)
        private val tvFormat: TextView = itemView.findViewById(R.id.tvFormat)

        fun bind(match: ScheduleMatch) {
            tvDate.text = match.dateFormatted
            tvSeries.text = match.seriesName
            tvMatchDesc.text = match.matchDesc
            tvTeams.text = "${match.team1ShortName} vs ${match.team2ShortName}"
            tvVenue.text = "${match.venue}, ${match.city}, ${match.country}"
            tvFormat.text = match.matchFormat
        }
    }

    class ScheduleDiffCallback : DiffUtil.ItemCallback<ScheduleMatch>() {
        override fun areItemsTheSame(oldItem: ScheduleMatch, newItem: ScheduleMatch): Boolean {
            return oldItem.matchId == newItem.matchId
        }

        override fun areContentsTheSame(oldItem: ScheduleMatch, newItem: ScheduleMatch): Boolean {
            return oldItem == newItem
        }
    }
}
