package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class EngAusOdiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.engausodi, container, false)
        setUpImageViewClickListeners(view)
        return view
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.enga_featured_image,
            R.id.enga_image_1,
            R.id.enga_image_2,
            R.id.enga_image_3,
            R.id.enga_image_4,
            R.id.enga_image_5,
            R.id.enga_image_6
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
            R.drawable.engausodi9,   // enga_featured_image
            R.drawable.engausodi1,
            R.drawable.engausodi2,
            R.drawable.engausodi3,
            R.drawable.engausodi4,
            R.drawable.engausodi5,
            R.drawable.engausodi6
        )

        val imageViewIds = listOf(
            R.id.enga_featured_image,
            R.id.enga_image_1,
            R.id.enga_image_2,
            R.id.enga_image_3,
            R.id.enga_image_4,
            R.id.enga_image_5,
            R.id.enga_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
