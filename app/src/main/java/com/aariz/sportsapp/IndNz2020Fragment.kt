package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class IndNz2020Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IndNz2020Fragment", "onCreateView called - loading indnz2020.xml")
        return try {
            val view = inflater.inflate(R.layout.indnz2020, container, false)
            Log.d("IndNz2020Fragment", "indnz2020.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.nz_featured_image, R.id.nz_image_1, R.id.nz_image_2,
            R.id.nz_image_3, R.id.nz_image_4, R.id.nz_image_5,
            R.id.nz_image_6, R.id.nz_image_7, R.id.nz_image_8,
            R.id.nz_image_9, R.id.nz_image_10, R.id.nz_image_11,
            R.id.nz_image_12
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
            R.drawable.indnz1,  R.drawable.indnz2,  R.drawable.indnz3,
            R.drawable.indnz4,  R.drawable.indnz5,  R.drawable.indnz6,
            R.drawable.indnz7,  R.drawable.indnz8,  R.drawable.indnz9,
            R.drawable.indnz10, R.drawable.indnz11, R.drawable.indnz12,
            R.drawable.indnz13
        )

        val imageViewIds = listOf(
            R.id.nz_featured_image, R.id.nz_image_1, R.id.nz_image_2,
            R.id.nz_image_3, R.id.nz_image_4, R.id.nz_image_5,
            R.id.nz_image_6, R.id.nz_image_7, R.id.nz_image_8,
            R.id.nz_image_9, R.id.nz_image_10, R.id.nz_image_11,
            R.id.nz_image_12
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
