package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment



class CT25Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("CT25Fragment", "onCreateView called - loading ct25.xml")
        return try {
            val view = inflater.inflate(R.layout.ct25, container, false)
            Log.d("CT25Fragment", "ct25.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.ct_featured_image, R.id.ct_image_1, R.id.ct_image_2,
            R.id.ct_image_3, R.id.ct_image_4, R.id.ct_image_5,
            R.id.ct_image_6, R.id.ct_image_7, R.id.ct_image_8,
            R.id.ct_image_9, R.id.ct_image_10
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
            R.drawable.ct1,  R.drawable.ct2,  R.drawable.ct3,
            R.drawable.ct4,  R.drawable.ct5,  R.drawable.ct6,
            R.drawable.ct7,  R.drawable. ct8,  R.drawable.ct9,
            R.drawable.ct10, R.drawable.ct11)

        val imageViewIds = listOf(
            R.id.ct_featured_image, R.id.ct_image_1, R.id.ct_image_2,
            R.id.ct_image_3, R.id.ct_image_4, R.id.ct_image_5,
            R.id.ct_image_6, R.id.ct_image_7, R.id.ct_image_8,
            R.id.ct_image_9, R.id.ct_image_10
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
