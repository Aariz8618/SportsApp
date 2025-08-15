package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class Winz12Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Winz12Fragment", "onCreateView called - loading winz12.xml")
        return try {
            val view = inflater.inflate(R.layout.winz12, container, false)
            Log.d("Winz12Fragment", "winz12.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.winz_featured_image, R.id.winz_image_1, R.id.winz_image_2,
            R.id.winz_image_3, R.id.winz_image_4, R.id.winz_image_5,
            R.id.winz_image_6
        )

        for (id in imageViewIds) {
            val imageView = view.findViewById<ImageView>(id)
            imageView.setOnClickListener {
                showFullScreenImage(it as ImageView, id)
            }
        }
    }

    private fun showFullScreenImage(imageView: ImageView, clickedImageId: Int) {
        val images = listOf(
            R.drawable.winz4, R.drawable.winz1, R.drawable.winz2,
            R.drawable.winz3, R.drawable.winz5, R.drawable.winz6,
            R.drawable.winz7
        )

        val imageViewIds = listOf(
            R.id.winz_featured_image, R.id.winz_image_1, R.id.winz_image_2,
            R.id.winz_image_3, R.id.winz_image_4, R.id.winz_image_5,
            R.id.winz_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
