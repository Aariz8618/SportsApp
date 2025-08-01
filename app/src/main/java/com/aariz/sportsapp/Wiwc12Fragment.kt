package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class Wiwc12Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Wiwc12Fragment", "onCreateView called - loading wiwc12.xml")
        return try {
            val view = inflater.inflate(R.layout.wiwc12, container, false)
            Log.d("Wiwc12Fragment", "wiwc12.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.wiwc12_featured_image, R.id.wiwc12_image_1, R.id.wiwc12_image_2,
            R.id.wiwc12_image_3, R.id.wiwc12_image_4, R.id.wiwc12_image_5,
            R.id.wiwc12_image_6
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
            R.drawable.witwc5, R.drawable.witwc4, R.drawable.witwc1,
            R.drawable.witwc2, R.drawable.witwc3, R.drawable.witwc6,
            R.drawable.witwc7
        )

        val imageViewIds = listOf(
            R.id.wiwc12_featured_image, R.id.wiwc12_image_1, R.id.wiwc12_image_2,
            R.id.wiwc12_image_3, R.id.wiwc12_image_4, R.id.wiwc12_image_5,
            R.id.wiwc12_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
