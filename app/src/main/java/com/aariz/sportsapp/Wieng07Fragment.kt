package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class Wieng07Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Wieng07Fragment", "onCreateView called - loading wieng07.xml")
        return try {
            val view = inflater.inflate(R.layout.wieng07, container, false)
            Log.d("Wieng07Fragment", "wieng07.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.wieng_featured_image, R.id.wieng_image_1, R.id.wieng_image_2,
            R.id.wieng_image_3, R.id.wieng_image_4, R.id.wieng_image_5,
            R.id.wieng_image_6
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
            R.drawable.wisl6, R.drawable.wisl2, R.drawable.wisl3,
            R.drawable.wisl4, R.drawable.wisl5, R.drawable.wisl1,
            R.drawable.wisl7
        )

        val imageViewIds = listOf(
            R.id.wieng_featured_image, R.id.wieng_image_1, R.id.wieng_image_2,
            R.id.wieng_image_3, R.id.wieng_image_4, R.id.wieng_image_5,
            R.id.wieng_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
