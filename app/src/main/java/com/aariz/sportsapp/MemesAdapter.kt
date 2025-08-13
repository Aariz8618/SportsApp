package com.aariz.sportsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MemesAdapter(
    private val memes: List<Meme>,
    private val onItemClick: (Meme) -> Unit = {}
) : RecyclerView.Adapter<MemesAdapter.MemeViewHolder>() {

    // Predefined random heights for Pinterest effect
    private val heightMultipliers = listOf(1.0f, 1.2f, 1.5f, 1.3f, 1.1f, 1.4f, 1.6f, 1.25f)

    inner class MemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val memeImageView: ImageView = itemView.findViewById(R.id.memeImageView)

        fun bind(meme: Meme, position: Int) {
            memeImageView.setImageResource(meme.imageResource)

            // Set varying heights for Pinterest effect
            val heightMultiplier = heightMultipliers[position % heightMultipliers.size]
            val baseHeight = (200 * itemView.context.resources.displayMetrics.density).toInt()
            val finalHeight = (baseHeight * heightMultiplier).toInt()

            val layoutParams = memeImageView.layoutParams
            layoutParams.height = finalHeight
            memeImageView.layoutParams = layoutParams

            // Make the entire card clickable
            itemView.setOnClickListener {
                onItemClick(meme)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meme_card, parent, false)
        return MemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        holder.bind(memes[position], position)
    }

    override fun getItemCount(): Int = memes.size
}
