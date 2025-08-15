package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.Bowler

class BowlerAdapter(private var items: List<Bowler>) : RecyclerView.Adapter<BowlerAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val overs: TextView = itemView.findViewById(R.id.tv_overs)
        val maidens: TextView = itemView.findViewById(R.id.tv_maidens)
        val runs: TextView = itemView.findViewById(R.id.tv_runs)
        val wickets: TextView = itemView.findViewById(R.id.tv_wickets)
        val econ: TextView = itemView.findViewById(R.id.tv_econ)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bowler, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val b = items[position]
        holder.name.text = b.name ?: "-"
        holder.overs.text = (b.o ?: 0.0).toString()
        holder.maidens.text = (b.m ?: 0).toString()
        holder.runs.text = (b.r ?: 0).toString()
        holder.wickets.text = (b.w ?: 0).toString()
        holder.econ.text = String.format("%.2f", b.econ ?: 0.0)
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<Bowler>) {
        items = newItems
        notifyDataSetChanged()
    }
}
