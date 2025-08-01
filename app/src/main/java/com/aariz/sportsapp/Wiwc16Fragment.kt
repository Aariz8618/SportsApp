package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class Wiwc16Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Wiwc16Fragment", "onCreateView called - loading wiwc16.xml")
        return try {
            val view = inflater.inflate(R.layout.wiwc16, container, false)
            Log.d("Wiwc16Fragment", "wiwc16.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.wiwc16_featured_image, R.id.wiwc16_image_1, R.id.wiwc16_image_2,
            R.id.wiwc16_image_3, R.id.wiwc16_image_4, R.id.wiwc16_image_5,
            R.id.wiwc16_image_6
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
            R.drawable.twiwc1, R.drawable.twiwc6, R.drawable.twiwc2,
            R.drawable.twiwc3, R.drawable.twiwc4, R.drawable.twiwc5,
            R.drawable.twiwc7
        )

        val imageViewIds = listOf(
            R.id.wiwc16_featured_image, R.id.wiwc16_image_1, R.id.wiwc16_image_2,
            R.id.wiwc16_image_3, R.id.wiwc16_image_4, R.id.wiwc16_image_5,
            R.id.wiwc16_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
