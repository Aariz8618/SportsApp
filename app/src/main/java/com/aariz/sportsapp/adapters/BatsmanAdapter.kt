package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.Batsman

class BatsmanAdapter(private var items: List<Batsman>) : RecyclerView.Adapter<BatsmanAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val runs: TextView = itemView.findViewById(R.id.tv_runs)
        val balls: TextView = itemView.findViewById(R.id.tv_balls)
        val fours: TextView = itemView.findViewById(R.id.tv_fours)
        val sixes: TextView = itemView.findViewById(R.id.tv_sixes)
        val sr: TextView = itemView.findViewById(R.id.tv_sr)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_batsman, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val b = items[position]
        holder.name.text = b.name ?: "-"
        holder.runs.text = (b.r ?: 0).toString()
        holder.balls.text = (b.b ?: 0).toString()
        holder.fours.text = (b.fours ?: 0).toString()
        holder.sixes.text = (b.sixes ?: 0).toString()
        holder.sr.text = String.format("%.1f", b.sr ?: 0.0)
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<Batsman>) {
        items = newItems
        notifyDataSetChanged()
    }
}
