package com.aariz.sportsapp

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FullscreenImagePagerAdapter(
    fragmentActivity: FragmentActivity,
    private val images: List<Int>
) : FragmentStateAdapter(fragmentActivity) {
    
    override fun getItemCount(): Int = images.size
    
    override fun createFragment(position: Int): Fragment {
        return try {
            if (position in images.indices) {
                FullscreenImageFragment.newInstance(images[position])
            } else {
                Log.e("FullscreenImagePagerAdapter", "Invalid position: $position")
                FullscreenImageFragment.newInstance(0)
            }
        } catch (e: Exception) {
            Log.e("FullscreenImagePagerAdapter", "Error creating fragment for position: $position", e)
            FullscreenImageFragment.newInstance(0)
        }
    }
} 