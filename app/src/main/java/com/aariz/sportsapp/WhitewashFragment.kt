package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class WhitewashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("WhitewashFragment", "onCreateView called - loading whitewash.xml")
        return try {
            val view = inflater.inflate(R.layout.whitewash, container, false)
            Log.d("WhitewashFragment", "whitewash.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.whitewash_featured_image, R.id.whitewash_image_1, R.id.whitewash_image_2,
            R.id.whitewash_image_3, R.id.whitewash_image_4, R.id.whitewash_image_5,
            R.id.whitewash_image_6, R.id.whitewash_image_7, R.id.whitewash_image_8,
            R.id.whitewash_image_9, R.id.whitewash_image_10, R.id.whitewash_image_11,
            R.id.whitewash_image_12, R.id.whitewash_image_13, R.id.whitewash_image_14
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
            R.drawable.whitewash15,  R.drawable.whitewash1,  R.drawable.whitewash2,
            R.drawable.whitewash3,  R.drawable.whitewash4,  R.drawable.whitewash5,
            R.drawable.whitewash6,  R.drawable.whitewash7,  R.drawable.whitewash8,
            R.drawable.whitewash9, R.drawable.whitewash10, R.drawable.whitewash11,
            R.drawable.whitewash12, R.drawable.whitewash13, R.drawable.whitewash14
        )

        val imageViewIds = listOf(
            R.id.whitewash_featured_image, R.id.whitewash_image_1, R.id.whitewash_image_2,
            R.id.whitewash_image_3, R.id.whitewash_image_4, R.id.whitewash_image_5,
            R.id.whitewash_image_6, R.id.whitewash_image_7, R.id.whitewash_image_8,
            R.id.whitewash_image_9, R.id.whitewash_image_10, R.id.whitewash_image_11,
            R.id.whitewash_image_12, R.id.whitewash_image_13, R.id.whitewash_image_14
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
