package com.aariz.sportsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.R
import com.aariz.sportsapp.model.SimpleShow
import com.bumptech.glide.Glide

class RecoAdapter(
    private val items: List<SimpleShow>,
    private val onClick: (SimpleShow) -> Unit
) : RecyclerView.Adapter<RecoAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imagePoster)
        val title: TextView = view.findViewById(R.id.txtTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card_poster, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.title.text = item.title


        // Load image with Glide
        Glide.with(holder.image.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_launcher_background) // Add a placeholder
            .error(R.drawable.ic_launcher_background) // Add an error image
            .into(holder.image)

        // Set click listener
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount(): Int = items.size
}