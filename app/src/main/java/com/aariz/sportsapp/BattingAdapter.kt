package com.aariz.sportsapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.Batsman

class BattingAdapter(private var batsmen: List<Batsman>) :
    RecyclerView.Adapter<BattingAdapter.BattingViewHolder>() {

    companion object {
        private const val TAG = "BattingAdapter"
    }

    class BattingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBatsmanName: TextView = itemView.findViewById(R.id.tv_player_name)
        val tvRuns: TextView = itemView.findViewById(R.id.tv_runs)
        val tvBalls: TextView = itemView.findViewById(R.id.tv_balls)
        val tvFours: TextView = itemView.findViewById(R.id.tv_fours)
        val tvSixes: TextView = itemView.findViewById(R.id.tv_sixes)
        val tvStrikeRate: TextView = itemView.findViewById(R.id.tv_strike_rate)
        val tvDismissal: TextView = itemView.findViewById(R.id.tv_dismissal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_batting_row, parent, false)
        return BattingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BattingViewHolder, position: Int) {
        val batsman = batsmen[position]

        try {
            // Batsman name with dismissal info
            val displayName = batsman.name ?: "Unknown"
            holder.tvBatsmanName.text = displayName

            // Runs
            holder.tvRuns.text = (batsman.r ?: 0).toString()

            // Balls faced
            holder.tvBalls.text = (batsman.b ?: 0).toString()

            // Fours
            holder.tvFours.text = (batsman.fours ?: 0).toString()

            // Sixes
            holder.tvSixes.text = (batsman.sixes ?: 0).toString()

            // Strike Rate
            val strikeRate = batsman.sr ?: 0.0
            holder.tvStrikeRate.text = String.format("%.2f", strikeRate)

            // Dismissal info (if available)
            val dismissal = batsman.dismissal ?: ""
            holder.tvDismissal.text = when {
                dismissal.contains("not out", ignoreCase = true) -> "not out"
                dismissal.isNotBlank() -> dismissal
                else -> "-"
            }

            Log.d(TAG, "Bound batsman: ${batsman.name} - ${batsman.r}(${batsman.b})")
        } catch (e: Exception) {
            Log.e(TAG, "Error binding batsman data at position $position", e)
            // Set default values on error
            holder.tvBatsmanName.text = "Unknown"
            holder.tvRuns.text = "0"
            holder.tvBalls.text = "0"
            holder.tvFours.text = "0"
            holder.tvSixes.text = "0"
            holder.tvStrikeRate.text = "0.00"
            holder.tvDismissal.text = "-"
        }
    }

    override fun getItemCount(): Int = batsmen.size

    fun updateBatsmen(newBatsmen: List<Batsman>) {
        Log.d(TAG, "Updating batsmen list: ${newBatsmen.size} batsmen")
        batsmen = newBatsmen
        notifyDataSetChanged()
    }
}