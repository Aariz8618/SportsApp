package com.aariz.sportsapp.legacy

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.Bowler

class LegacyBowlingAdapter(private var bowlers: List<Bowler>) :
    RecyclerView.Adapter<LegacyBowlingAdapter.BowlingViewHolder>() {

    companion object {
        private const val TAG = "LegacyBowlingAdapter"
    }

    class BowlingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBowlerName: TextView = itemView.findViewById(R.id.tv_bowler_name)
        val tvOvers: TextView = itemView.findViewById(R.id.tv_overs)
        val tvMaidens: TextView = itemView.findViewById(R.id.tv_maidens)
        val tvRunsGiven: TextView = itemView.findViewById(R.id.tv_runs_given)
        val tvWickets: TextView = itemView.findViewById(R.id.tv_wickets)
        val tvEconomy: TextView = itemView.findViewById(R.id.tv_economy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BowlingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bowling_row, parent, false)
        return BowlingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BowlingViewHolder, position: Int) {
        val bowler = bowlers[position]

        try {
            // Bowler name
            holder.tvBowlerName.text = bowler.name ?: "Unknown"

            // Overs
            val overs = bowler.o ?: 0.0
            holder.tvOvers.text = String.format("%.1f", overs)

            // Maidens
            holder.tvMaidens.text = (bowler.m ?: 0).toString()

            // Runs
            holder.tvRunsGiven.text = (bowler.r ?: 0).toString()

            // Wickets
            holder.tvWickets.text = (bowler.w ?: 0).toString()

            // Economy rate
            val economy = bowler.econ ?: 0.0
            holder.tvEconomy.text = String.format("%.2f", economy)

            Log.d(TAG, "Bound bowler: ${bowler.name} - ${bowler.w}/${bowler.r} (${bowler.o} ov)")
        } catch (e: Exception) {
            Log.e(TAG, "Error binding bowler data at position $position", e)
            // Set default values on error
            holder.tvBowlerName.text = "Unknown"
            holder.tvOvers.text = "0.0"
            holder.tvMaidens.text = "0"
            holder.tvRunsGiven.text = "0"
            holder.tvWickets.text = "0"
            holder.tvEconomy.text = "0.00"
        }
    }

    override fun getItemCount(): Int = bowlers.size

    fun updateBowlers(newBowlers: List<Bowler>) {
        Log.d(TAG, "Updating bowlers list: ${newBowlers.size} bowlers")
        bowlers = newBowlers
        notifyDataSetChanged()
    }
}