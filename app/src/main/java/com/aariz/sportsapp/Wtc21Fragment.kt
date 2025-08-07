package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Wtc21Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Wtc21Fragment", "onCreateView called - loading wtc21.xml")
        return try {
            val view = inflater.inflate(R.layout.wtc21, container, false)
            Log.d("Wtc21Fragment", "wtc21.xml layout inflated successfully")
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
            title = "World Test Championship 2021"
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.wtc21_featured_image, R.id.wtc21_image_1, R.id.wtc21_image_2,
            R.id.wtc21_image_3, R.id.wtc21_image_4, R.id.wtc21_image_5, R.id.wtc21_image_6,
            R.id.wtc21_image_7, R.id.wtc21_image_8, R.id.wtc21_image_9, R.id.wtc21_image_10,
            R.id.wtc21_image_11, R.id.wtc21_image_12, R.id.wtc21_image_13, R.id.wtc21_image_14
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
            R.drawable.nzwtc10, R.drawable.nzwtc7, R.drawable.nzwtc15,
            R.drawable.nzwtc2, R.drawable.nzwtc3, R.drawable.nzwtc4,
            R.drawable.nzwtc5, R.drawable.nzwtc6, R.drawable.nzwtc8,
            R.drawable.nzwtc9, R.drawable.nzwtc11, R.drawable.nzwtc1,
            R.drawable.nzwtc12, R.drawable.nzwtc13, R.drawable.nzwtc14
        )

        val imageViewIds = listOf(
            R.id.wtc21_featured_image, R.id.wtc21_image_1, R.id.wtc21_image_2,
            R.id.wtc21_image_3, R.id.wtc21_image_4, R.id.wtc21_image_5,
            R.id.wtc21_image_6, R.id.wtc21_image_7, R.id.wtc21_image_8,
            R.id.wtc21_image_9, R.id.wtc21_image_10, R.id.wtc21_image_11,
            R.id.wtc21_image_12, R.id.wtc21_image_13, R.id.wtc21_image_14
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
