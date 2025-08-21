package com.aariz.sportsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import com.aariz.sportsapp.databinding.ItemFullscreenMemeBinding

class FullScreenMemeAdapter(
    private val memes: List<Meme>,
    private val onImageClick: () -> Unit
) : RecyclerView.Adapter<FullScreenMemeAdapter.FullScreenMemeViewHolder>() {

    inner class FullScreenMemeViewHolder(
        private val binding: ItemFullscreenMemeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meme: Meme) {
            binding.photoView.apply {
                // Set the image resource
                setImageResource(meme.imageResource)
                
                // Enable zoom controls
                isZoomable = true
                maximumScale = 5.0f
                minimumScale = 1.0f
                mediumScale = 2.5f
                
                // Set scale type for best fit
                scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
                
                // Handle single tap to toggle UI
                setOnPhotoTapListener { _, _, _ ->
                    onImageClick()
                }
                
                // Reset zoom on new image
                setScale(1.0f, true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullScreenMemeViewHolder {
        val binding = ItemFullscreenMemeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FullScreenMemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FullScreenMemeViewHolder, position: Int) {
        holder.bind(memes[position])
    }

    override fun getItemCount(): Int = memes.size
}
