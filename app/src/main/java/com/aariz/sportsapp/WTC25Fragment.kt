package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment



class WTC25Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("WTC25Fragment", "onCreateView called - loading wtc25.xml")
        return try {
            val view = inflater.inflate(R.layout.wtc25, container, false)
            Log.d("WTC25Fragment", "wtc25.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.sawtc_featured_image, R.id.sawtc_image_1, R.id.sawtc_image_2,
            R.id.sawtc_image_3, R.id.sawtc_image_4, R.id.sawtc_image_5,
            R.id.sawtc_image_6, R.id.sawtc_image_7, R.id.sawtc_image_8,
            R.id.sawtc_image_9, R.id.sawtc_image_10
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
            R.drawable.sawtc1,  R.drawable.sawtc2,  R.drawable.sawtc3,
            R.drawable.sawtc4,  R.drawable.sawtc5,  R.drawable.sawtc6,
            R.drawable.sawtc7,  R.drawable. sawtc8,  R.drawable.sawtc9,
            R.drawable.sawtc10, R.drawable.sawtc11)

        val imageViewIds = listOf(
            R.id.sawtc_featured_image, R.id.sawtc_image_1, R.id.sawtc_image_2,
            R.id.sawtc_image_3, R.id.sawtc_image_4, R.id.sawtc_image_5,
            R.id.sawtc_image_6, R.id.sawtc_image_7, R.id.sawtc_image_8,
            R.id.sawtc_image_9, R.id.sawtc_image_10
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
