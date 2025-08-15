package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class Iccwc2023Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Iccwc2023Fragment", "onCreateView called - loading iccwc2023.xml")
        return try {
            val view = inflater.inflate(R.layout.iccwc2023, container, false)
            Log.d("Iccwc2023Fragment", "iccwc2023.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.iccwc2023_featured_image,
            R.id.iccwc2023_image_1,
            R.id.iccwc2023_image_2,
            R.id.iccwc2023_image_3,
            R.id.iccwc2023_image_4,
            R.id.iccwc2023_image_5,
            R.id.iccwc2023_image_6
        )

        for (id in imageViewIds) {
            val imageView = view.findViewById<ImageView>(id)
            imageView?.setOnClickListener {
                showFullScreenImage(id)
            }
        }
    }

    private fun showFullScreenImage(clickedImageId: Int) {
        val images = listOf(
            R.drawable.auswc3, // iccwc2023_featured_image
            R.drawable.auswc7,
            R.drawable.auswc1,
            R.drawable.auswc2,
            R.drawable.auswc4,
            R.drawable.auswc5,
            R.drawable.auswc6
        )

        val imageViewIds = listOf(
            R.id.iccwc2023_featured_image,
            R.id.iccwc2023_image_1,
            R.id.iccwc2023_image_2,
            R.id.iccwc2023_image_3,
            R.id.iccwc2023_image_4,
            R.id.iccwc2023_image_5,
            R.id.iccwc2023_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
