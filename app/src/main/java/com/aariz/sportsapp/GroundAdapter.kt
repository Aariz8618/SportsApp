package com.aariz.sportsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroundAdapter(
    private val grounds: List<CricketGround>,
    private val onItemClick: (CricketGround) -> Unit
) : RecyclerView.Adapter<GroundAdapter.GroundViewHolder>() {

    inner class GroundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageGround)
        val location: TextView = itemView.findViewById(R.id.tvGroundLocation)
        val footer: TextView = itemView.findViewById(R.id.tvGroundFooter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroundViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ground, parent, false)
        return GroundViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroundViewHolder, position: Int) {
        val ground = grounds[position]
        holder.location.text = ground.location
        holder.footer.text = ground.name
        holder.image.setImageResource(ground.imageResId)

        holder.itemView.setOnClickListener { onItemClick(ground) }
    }

    override fun getItemCount() = grounds.size
}
