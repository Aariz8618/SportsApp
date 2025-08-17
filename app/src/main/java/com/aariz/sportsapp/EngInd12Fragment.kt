package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class EngInd12Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.engind12, container, false)
        setUpImageViewClickListeners(view)
        return view
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.engind_featured_image,
            R.id.engind_image_1,
            R.id.engind_image_2,
            R.id.engind_image_3,
            R.id.engind_image_4,
            R.id.engind_image_5,
            R.id.engind_image_6
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
            R.drawable.engindtest7,   // engind_featured_image
            R.drawable.engindtest1,
            R.drawable.engindtest2,
            R.drawable.engindtest3,
            R.drawable.engindtest4,
            R.drawable.engindtest5,
            R.drawable.engindtest6
        )

        val imageViewIds = listOf(
            R.id.engind_featured_image,
            R.id.engind_image_1,
            R.id.engind_image_2,
            R.id.engind_image_3,
            R.id.engind_image_4,
            R.id.engind_image_5,
            R.id.engind_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
