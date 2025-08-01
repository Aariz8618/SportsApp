package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Wtc23ausFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Wtc23ausFragment", "onCreateView called - loading wtc23aus.xml")
        return try {
            val view = inflater.inflate(R.layout.wtc23aus, container, false)
            Log.d("Wtc23ausFragment", "wtc23aus.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enable back button and set title
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "WTC Final 2023 - Australia"
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.wtc23aus_featured_image, R.id.wtc23aus_image_1, R.id.wtc23aus_image_2,
            R.id.wtc23aus_image_3, R.id.wtc23aus_image_4, R.id.wtc23aus_image_5,
            R.id.wtc23aus_image_6, R.id.wtc23aus_image_7, R.id.wtc23aus_image_8,
            R.id.wtc23aus_image_9, R.id.wtc23aus_image_10, R.id.wtc23aus_image_11, R.id.wtc23aus_image_12
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
            R.drawable.auswtc2, R.drawable.auswtc1, R.drawable.auswtc3,
            R.drawable.auswtc4, R.drawable.auswtc5, R.drawable.auswtc6,
            R.drawable.auswtc7, R.drawable.auswtc8, R.drawable.auswtc9,
            R.drawable.auswtc10,R.drawable.auswtc11,R.drawable.auswtc12,
            R.drawable.auswtc13
        )

        val imageViewIds = listOf(
            R.id.wtc23aus_featured_image, R.id.wtc23aus_image_1, R.id.wtc23aus_image_2,
            R.id.wtc23aus_image_3, R.id.wtc23aus_image_4, R.id.wtc23aus_image_5,
            R.id.wtc23aus_image_6, R.id.wtc23aus_image_7, R.id.wtc23aus_image_8,
            R.id.wtc23aus_image_9, R.id.wtc23aus_image_10, R.id.wtc23aus_image_11, R.id.wtc23aus_image_12
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
