package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.models.Inning

class InningsAdapter(private var items: List<Inning>) : RecyclerView.Adapter<InningsAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_inning_title)
        val meta: TextView = itemView.findViewById(R.id.tv_inning_meta)
        val rvBatsmen: RecyclerView = itemView.findViewById(R.id.rv_batsmen)
        val rvBowlers: RecyclerView = itemView.findViewById(R.id.rv_bowlers)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inning_scorecard, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val inning = items[position]
        val total = "${inning.r ?: 0}/${inning.w ?: 0} (${inning.o ?: 0.0} Ov)"
        holder.title.text = "${inning.inning ?: "Innings"} - $total"
        val metaParts = mutableListOf<String>()
        inning.target?.let { metaParts.add("Target: $it") }
        inning.rrr?.let { metaParts.add("RRR: ${String.format("%.2f", it)}") }
        holder.meta.text = metaParts.joinToString("    ")

        holder.rvBatsmen.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.rvBowlers.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.rvBatsmen.adapter = BatsmanAdapter(inning.batsmen ?: emptyList())
        holder.rvBowlers.adapter = BowlerAdapter(inning.bowlers ?: emptyList())
        holder.rvBatsmen.isNestedScrollingEnabled = false
        holder.rvBowlers.isNestedScrollingEnabled = false
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<Inning>) {
        items = newItems
        notifyDataSetChanged()
    }
}
