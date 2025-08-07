package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class NzWc15Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("NzWc15Fragment", "onCreateView called - loading nzwc15.xml")
        return try {
            val view = inflater.inflate(R.layout.nzwc15, container, false)
            Log.d("NzWc15Fragment", "nzwc15.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.nzwc15_featured_image, R.id.nzwc15_image_1, R.id.nzwc15_image_2,
            R.id.nzwc15_image_3, R.id.nzwc15_image_4, R.id.nzwc15_image_5,
            R.id.nzwc15_image_6
        )

        for (id in imageViewIds) {
            val imageView = view.findViewById<ImageView>(id)
            imageView.setOnClickListener {
                showFullScreenImage(it as ImageView, id)
            }
        }
    }

    private fun showFullScreenImage(imageView: ImageView, clickedImageId: Int) {
        // Define the list of images in order
        val images = listOf(
            R.drawable.nzwc4,
            R.drawable.nzwc5,
            R.drawable.nzwc1,
            R.drawable.nzwc2,
            R.drawable.nzwc3,
            R.drawable.nzwc6,
            R.drawable.nzwc7
        )

        // Find the position of the clicked image
        val imageViewIds = listOf(
            R.id.nzwc15_featured_image, R.id.nzwc15_image_1, R.id.nzwc15_image_2,
            R.id.nzwc15_image_3, R.id.nzwc15_image_4, R.id.nzwc15_image_5,
            R.id.nzwc15_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        // Launch the fullscreen activity with swipe functionality
        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
