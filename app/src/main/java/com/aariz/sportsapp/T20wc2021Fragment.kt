package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class T20wc2021Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("T20wc2021Fragment", "onCreateView called - loading t20wc2021.xml")
        return try {
            val view = inflater.inflate(R.layout.t20wc2021, container, false)
            Log.d("T20wc2021Fragment", "t20wc2021.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.t20wc2021_featured_image, R.id.t20wc2021_image_1, R.id.t20wc2021_image_2,
            R.id.t20wc2021_image_3, R.id.t20wc2021_image_4, R.id.t20wc2021_image_5,
            R.id.t20wc2021_image_6
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
            R.drawable.t20wca2,
            R.drawable.t20wca1,
            R.drawable.t20wca3,
            R.drawable.t20wca5,
            R.drawable.t20wca6,
            R.drawable.t20wca7,
            R.drawable.t20wca4
        )

        val imageViewIds = listOf(
            R.id.t20wc2021_featured_image, R.id.t20wc2021_image_1, R.id.t20wc2021_image_2,
            R.id.t20wc2021_image_3, R.id.t20wc2021_image_4, R.id.t20wc2021_image_5,
            R.id.t20wc2021_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
