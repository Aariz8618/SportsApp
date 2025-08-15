package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class IndtAtFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IndtAtFragment", "onCreateView called - loading indtat.xml")
        return try {
            val view = inflater.inflate(R.layout.indtat, container, false)
            Log.d("IndtAtFragment", "indtat.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            Log.e("IndtAtFragment", "Error in onCreateView", e)
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        try {
            val imageViewIds = listOf(
                R.id.att_featured_image, R.id.att_image_1, R.id.att_image_2,
                R.id.att_image_3, R.id.att_image_4, R.id.att_image_5,
                R.id.att_image_6, R.id.att_image_7, R.id.att_image_8,
                R.id.att_image_9, R.id.att_image_10, R.id.att_image_11,
                R.id.att_image_12, R.id.att_image_13, R.id.att_image_14
            )

            for (id in imageViewIds) {
                try {
                    val imageView = view.findViewById<ImageView>(id)
                    imageView?.setOnClickListener {
                        showFullScreenImage(it as ImageView, id)
                    }
                } catch (e: Exception) {
                    Log.e("IndtAtFragment", "Error setting click listener for image ID: $id", e)
                }
            }
        } catch (e: Exception) {
            Log.e("IndtAtFragment", "Error setting up image click listeners", e)
        }
    }

    private fun showFullScreenImage(imageView: ImageView, clickedImageId: Int) {
        try {
            val images = listOf(
                R.drawable.att1,  R.drawable.att2,  R.drawable.att3,
                R.drawable.att4,  R.drawable.att5,  R.drawable.att6,
                R.drawable.att7,  R.drawable.att8,  R.drawable.att9,
                R.drawable.att10, R.drawable.att11, R.drawable.att12,
                R.drawable.att13, R.drawable.att14, R.drawable.att15
            )

            val imageViewIds = listOf(
                R.id.att_featured_image, R.id.att_image_1, R.id.att_image_2,
                R.id.att_image_3, R.id.att_image_4, R.id.att_image_5,
                R.id.att_image_6, R.id.att_image_7, R.id.att_image_8,
                R.id.att_image_9, R.id.att_image_10, R.id.att_image_11,
                R.id.att_image_12, R.id.att_image_13, R.id.att_image_14
            )

            val position = imageViewIds.indexOf(clickedImageId)
            
            if (position != -1 && position < images.size) {
                FullscreenImageActivity.start(requireContext(), images, position)
            } else {
                Log.e("IndtAtFragment", "Invalid position: $position for clickedImageId: $clickedImageId")
            }
        } catch (e: Exception) {
            Log.e("IndtAtFragment", "Error showing fullscreen image", e)
        }
    }
}
