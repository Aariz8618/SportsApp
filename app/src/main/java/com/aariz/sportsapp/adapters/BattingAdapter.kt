package com.aariz.sportsapp.legacy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R

data class BattingStats(
    val name: String,
    val runs: Int,
    val balls: Int,
    val fours: Int,
    val sixes: Int,
    val status: String
) {
    val strikeRate: Double
        get() = if (balls > 0) (runs.toDouble() / balls) * 100 else 0.0
}

class LegacyBattingStatsAdapter(private val battingStats: List<BattingStats>) :
    RecyclerView.Adapter<LegacyBattingStatsAdapter.BattingViewHolder>() {

    class BattingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPlayerName: TextView = itemView.findViewById(R.id.tv_player_name)
        val tvDismissal: TextView = itemView.findViewById(R.id.tv_dismissal)
        val tvRuns: TextView = itemView.findViewById(R.id.tv_runs)
        val tvBalls: TextView = itemView.findViewById(R.id.tv_balls)
        val tvFours: TextView = itemView.findViewById(R.id.tv_fours)
        val tvSixes: TextView = itemView.findViewById(R.id.tv_sixes)
        val tvStrikeRate: TextView = itemView.findViewById(R.id.tv_strike_rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_batting_row, parent, false)
        return BattingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BattingViewHolder, position: Int) {
        val stats = battingStats[position]
        
        holder.tvPlayerName.text = stats.name
        holder.tvDismissal.text = stats.status
        holder.tvRuns.text = stats.runs.toString()
        holder.tvBalls.text = stats.balls.toString()
        holder.tvFours.text = stats.fours.toString()
        holder.tvSixes.text = stats.sixes.toString()
        holder.tvStrikeRate.text = String.format("%.1f", stats.strikeRate)
    }

    override fun getItemCount(): Int = battingStats.size
}
