package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class FullscreenImageFragment : Fragment() {

    private var imageResId: Int = 0

    companion object {
        private const val TAG = "FullscreenImageFragment"
        private const val ARG_IMAGE_RES_ID = "image_res_id"

        fun newInstance(imageResId: Int): FullscreenImageFragment {
            Log.d(TAG, "Creating new instance with imageResId: $imageResId")
            return FullscreenImageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_IMAGE_RES_ID, imageResId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageResId = arguments?.getInt(ARG_IMAGE_RES_ID) ?: 0
        Log.d(TAG, "Fragment created with imageResId: $imageResId")

        if (imageResId == 0) {
            Log.w(TAG, "Invalid image resource ID received")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView called")
        return try {
            inflater.inflate(R.layout.fragment_fullscreen_image, container, false)
        } catch (e: Exception) {
            Log.e(TAG, "Error inflating layout", e)
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            val imageView = view.findViewById<ImageView>(R.id.swipeableImageView)

            if (imageView != null) {
                if (imageResId != 0) {
                    // Validate that the resource exists
                    try {
                        // Check if resource exists
                        val resources = requireContext().resources
                        resources.getResourceName(imageResId) // This will throw if resource doesn't exist

                        imageView.setImageResource(imageResId)
                        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                        Log.d(TAG, "Image set successfully: $imageResId")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error setting image resource: $imageResId", e)
                        // Set a fallback image
                        imageView.setImageResource(android.R.drawable.ic_menu_gallery)
                        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    }
                } else {
                    Log.w(TAG, "No valid image resource ID, setting fallback")
                    imageView.setImageResource(android.R.drawable.ic_menu_gallery)
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                }
            } else {
                Log.e(TAG, "ImageView not found in layout")
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error in onViewCreated", e)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView called")
    }
}