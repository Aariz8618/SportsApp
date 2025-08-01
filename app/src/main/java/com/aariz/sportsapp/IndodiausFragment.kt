package com.aariz.sportsapp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

class IndodiausFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IndodiausFragment", "onCreateView called - loading indodiaus.xml")
        return try {
            val view = inflater.inflate(R.layout.indodiaus, container, false)
            Log.d("IndodiausFragment", "indodiaus.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.aus_featured_image, R.id.aus_image_1, R.id.aus_image_2,
            R.id.aus_image_3, R.id.aus_image_4, R.id.aus_image_5,
            R.id.aus_image_6
        )

        for (id in imageViewIds) {
            val imageView = view.findViewById<ImageView>(id)
            imageView.setOnClickListener {
                showFullScreenImage(it as ImageView, id)
            }
        }
    }

    private fun showFullScreenImage(imageView: ImageView, clickedImageId: Int) {
        // Define the list of images in order
        val images = listOf(
            R.drawable.vsaus1,  // aus_featured_image
            R.drawable.vsaus2,  // aus_image_1
            R.drawable.vsaus3,  // aus_image_2
            R.drawable.vsaus6,  // aus_image_3
            R.drawable.vsaus5,  // aus_image_4
            R.drawable.vsaus4,  // aus_image_5
            R.drawable.vsaus7   // aus_image_6
        )
        
        // Find the position of the clicked image
        val imageViewIds = listOf(
            R.id.aus_featured_image, R.id.aus_image_1, R.id.aus_image_2,
            R.id.aus_image_3, R.id.aus_image_4, R.id.aus_image_5,
            R.id.aus_image_6
        )
        
        val position = imageViewIds.indexOf(clickedImageId)
        
        // Launch the fullscreen activity with swipe functionality
        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
