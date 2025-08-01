package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class IndodiwcFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IndodiwcFragment", "onCreateView called - loading indodiwc.xml")
        return try {
            val view = inflater.inflate(R.layout.indodiwc, container, false)
            Log.d("IndodiwcFragment", "indodiwc.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.wc_featured_image, R.id.wc_image_1, R.id.wc_image_2,
            R.id.wc_image_3, R.id.wc_image_4, R.id.wc_image_5,
            R.id.wc_image_6, R.id.wc_image_7, R.id.wc_image_8
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
            R.drawable.wc6, R.drawable.wc1, R.drawable.wc2,
            R.drawable.wc7, R.drawable.wc9, R.drawable.wc5,
            R.drawable.wc3, R.drawable.wc4, R.drawable.wc8
        )

        val imageViewIds = listOf(
            R.id.wc_featured_image, R.id.wc_image_1, R.id.wc_image_2,
            R.id.wc_image_3, R.id.wc_image_4, R.id.wc_image_5,
            R.id.wc_image_6, R.id.wc_image_7, R.id.wc_image_8
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
