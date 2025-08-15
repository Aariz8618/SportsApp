package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class EngAshesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.engashes, container, false)
        setUpImageViewClickListeners(view)
        return view
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.ashes_featured_image,
            R.id.ashes_image_1,
            R.id.ashes_image_2,
            R.id.ashes_image_3,
            R.id.ashes_image_4,
            R.id.ashes_image_5,
            R.id.ashes_image_6
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
            R.drawable.eashes6,   // ashes_featured_image
            R.drawable.eashes9,
            R.drawable.eashes7,
            R.drawable.eashes1,
            R.drawable.eashes2,
            R.drawable.eashes3,
            R.drawable.eashes5
        )

        val imageViewIds = listOf(
            R.id.ashes_featured_image,
            R.id.ashes_image_1,
            R.id.ashes_image_2,
            R.id.ashes_image_3,
            R.id.ashes_image_4,
            R.id.ashes_image_5,
            R.id.ashes_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
