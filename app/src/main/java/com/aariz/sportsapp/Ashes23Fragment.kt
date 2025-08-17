package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class Ashes23Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Ashes23Fragment", "onCreateView called - loading ashes23.xml")
        return try {
            val view = inflater.inflate(R.layout.ashes23, container, false)
            Log.d("Ashes23Fragment", "ashes23.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.ashes23_featured_image,
            R.id.ashes23_image_1,
            R.id.ashes23_image_2,
            R.id.ashes23_image_3,
            R.id.ashes23_image_4,
            R.id.ashes23_image_5,
            R.id.ashes23_image_6,
            R.id.ashes23_image_7,
            R.id.ashes23_image_8,
            R.id.ashes23_image_9,
            R.id.ashes23_image_10,
            R.id.ashes23_image_11,
            R.id.ashes23_image_12
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
            R.drawable.aashesh13,   // ashes23_featured_image
            R.drawable.aashesh14,
            R.drawable.aashesh12,
            R.drawable.aashesh1,
            R.drawable.aashesh2,
            R.drawable.aashesh3,
            R.drawable.aashesh4,
            R.drawable.aashesh5,
            R.drawable.aashesh6,
            R.drawable.aashesh7,
            R.drawable.aashesh9,
            R.drawable.aashesh11,
            R.drawable.aashesh8
        )

        val imageViewIds = listOf(
            R.id.ashes23_featured_image,
            R.id.ashes23_image_1,
            R.id.ashes23_image_2,
            R.id.ashes23_image_3,
            R.id.ashes23_image_4,
            R.id.ashes23_image_5,
            R.id.ashes23_image_6,
            R.id.ashes23_image_7,
            R.id.ashes23_image_8,
            R.id.ashes23_image_9,
            R.id.ashes23_image_10,
            R.id.ashes23_image_11,
            R.id.ashes23_image_12
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
