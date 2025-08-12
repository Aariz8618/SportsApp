package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment



class AusWiTestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("AusWiTestFragment", "onCreateView called - loading auswitest.xml")
        return try {
            val view = inflater.inflate(R.layout.auswitest, container, false)
            Log.d("AusWiTestFragment", "auswitest.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.auswi_featured_image, R.id.auswi_image_1, R.id.auswi_image_2,
            R.id.auswi_image_3, R.id.auswi_image_4, R.id.auswi_image_5,
            R.id.auswi_image_6, R.id.auswi_image_7, R.id.auswi_image_8,
            R.id.auswi_image_9, R.id.auswi_image_10
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
            R.drawable.auswi1,  R.drawable.auswi2,  R.drawable.auswi3,
            R.drawable.auswi4,  R.drawable.auswi5,  R.drawable.auswi6,
            R.drawable.auswi7,  R.drawable.auswi8,  R.drawable.auswi9,
            R.drawable.auswi10, R.drawable.auswi11)

        val imageViewIds = listOf(
            R.id.auswi_featured_image, R.id.auswi_image_1, R.id.auswi_image_2,
            R.id.auswi_image_3, R.id.auswi_image_4, R.id.auswi_image_5,
            R.id.auswi_image_6, R.id.auswi_image_7, R.id.auswi_image_8,
            R.id.auswi_image_9, R.id.auswi_image_10
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
