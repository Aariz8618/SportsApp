package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class NzTwc21Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("NzTwc21Fragment", "onCreateView called - loading nztwc21.xml")
        return try {
            val view = inflater.inflate(R.layout.nztwc21, container, false)
            Log.d("NzTwc21Fragment", "nztwc21.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.nztwc21_featured_image,
            R.id.nztwc21_image_1,
            R.id.nztwc21_image_2,
            R.id.nztwc21_image_3,
            R.id.nztwc21_image_4,
            R.id.nztwc21_image_5,
            R.id.nztwc21_image_6
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
            R.drawable.t20wcnz6,
            R.drawable.t20wcnz2,
            R.drawable.t20wcnz1,
            R.drawable.t20wcnz3,
            R.drawable.t20wcnz4,
            R.drawable.t20wcnz5,
            R.drawable.t20wcnz7
        )

        val imageViewIds = listOf(
            R.id.nztwc21_featured_image,
            R.id.nztwc21_image_1,
            R.id.nztwc21_image_2,
            R.id.nztwc21_image_3,
            R.id.nztwc21_image_4,
            R.id.nztwc21_image_5,
            R.id.nztwc21_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
