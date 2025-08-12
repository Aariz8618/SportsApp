package com.aariz.sportsapp

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FullscreenImagePagerAdapter(
    activity: FragmentActivity,
    private val images: List<Int>
) : FragmentStateAdapter(activity) {

    companion object {
        private const val TAG = "FullscreenImagePagerAdapter"
    }

    init {
        Log.d(TAG, "Adapter created with ${images.size} images")
        if (images.isEmpty()) {
            Log.w(TAG, "Warning: Empty images list provided to adapter")
        }

        // Log all image resource IDs for debugging
        images.forEachIndexed { index, imageResId ->
            Log.d(TAG, "Image $index: $imageResId")
        }
    }

    override fun getItemCount(): Int {
        val count = images.size
        Log.d(TAG, "getItemCount: $count")
        return count
    }

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment called for position: $position")

        return try {
            if (position >= 0 && position < images.size) {
                val imageResId = images[position]
                Log.d(TAG, "Creating fragment for position $position with imageResId: $imageResId")
                FullscreenImageFragment.newInstance(imageResId)
            } else {
                Log.e(TAG, "Invalid position: $position, images size: ${images.size}")
                // Return empty fragment as fallback
                FullscreenImageFragment.newInstance(android.R.drawable.ic_menu_gallery)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating fragment for position $position", e)
            // Return fallback fragment
            FullscreenImageFragment.newInstance(android.R.drawable.ic_menu_gallery)
        }
    }
}