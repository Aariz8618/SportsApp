package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class ShamarausFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ShamarausFragment", "onCreateView called - loading shamaraus.xml")
        return try {
            val view = inflater.inflate(R.layout.shamaraus, container, false)
            Log.d("ShamarausFragment", "shamaraus.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.shamar_featured_image, R.id.shamar_image_1, R.id.shamar_image_2,
            R.id.shamar_image_3, R.id.shamar_image_4, R.id.shamar_image_5,
            R.id.shamar_image_6
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
            R.drawable.shamar6,
            R.drawable.shamar7,
            R.drawable.shamar1,
            R.drawable.shamar2,
            R.drawable.shamar3,
            R.drawable.shamar4,
            R.drawable.shamar5
        )

        val imageViewIds = listOf(
            R.id.shamar_featured_image, R.id.shamar_image_1, R.id.shamar_image_2,
            R.id.shamar_image_3, R.id.shamar_image_4, R.id.shamar_image_5,
            R.id.shamar_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
