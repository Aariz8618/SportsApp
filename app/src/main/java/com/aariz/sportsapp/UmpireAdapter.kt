package com.aariz.sportsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UmpireAdapter(
    private val umpireList: List<Umpire>,
    private val onUmpireClick: (Umpire) -> Unit
) : RecyclerView.Adapter<UmpireAdapter.UmpireViewHolder>() {

    inner class UmpireViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.umpireName)
        val country: TextView = itemView.findViewById(R.id.umpireCountry)
        val image: ImageView = itemView.findViewById(R.id.umpireImage)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onUmpireClick(umpireList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UmpireViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_umpire, parent, false)
        return UmpireViewHolder(view)
    }

    override fun onBindViewHolder(holder: UmpireViewHolder, position: Int) {
        val umpire = umpireList[position]
        holder.name.text = umpire.name
        holder.country.text = umpire.country
        holder.image.setImageResource(umpire.imageResId)
    }

    override fun getItemCount() = umpireList.size
}
