package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class AusEngT20Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.ausengt20, container, false)
        setUpImageViewClickListeners(view)
        return view
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.ausengt20_featured_image,
            R.id.ausengt20_image_1,
            R.id.ausengt20_image_2,
            R.id.ausengt20_image_3,
            R.id.ausengt20_image_4,
            R.id.ausengt20_image_5,
            R.id.ausengt20_image_6
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
            R.drawable.auseng5,   // ausengt20_featured_image
            R.drawable.auseng7,
            R.drawable.auseng1,
            R.drawable.auseng2,
            R.drawable.auseng3,
            R.drawable.auseng4,
            R.drawable.auseng6
        )

        val imageViewIds = listOf(
            R.id.ausengt20_featured_image,
            R.id.ausengt20_image_1,
            R.id.ausengt20_image_2,
            R.id.ausengt20_image_3,
            R.id.ausengt20_image_4,
            R.id.ausengt20_image_5,
            R.id.ausengt20_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
