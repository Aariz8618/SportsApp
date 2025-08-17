package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class Lara400Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Lara400Fragment", "onCreateView called - loading lara400.xml")
        return try {
            val view = inflater.inflate(R.layout.lara400, container, false)
            Log.d("Lara400Fragment", "lara400.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.lara400_featured_image,
            R.id.lara400_image_1,
            R.id.lara400_image_2,
            R.id.lara400_image_3,
            R.id.lara400_image_4,
            R.id.lara400_image_5,
            R.id.lara400_image_6
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
            R.drawable.lara9,
            R.drawable.lara1,
            R.drawable.lara2,
            R.drawable.lara3,
            R.drawable.lara4,
            R.drawable.lara7,
            R.drawable.lara8
        )

        val imageViewIds = listOf(
            R.id.lara400_featured_image,
            R.id.lara400_image_1,
            R.id.lara400_image_2,
            R.id.lara400_image_3,
            R.id.lara400_image_4,
            R.id.lara400_image_5,
            R.id.lara400_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
