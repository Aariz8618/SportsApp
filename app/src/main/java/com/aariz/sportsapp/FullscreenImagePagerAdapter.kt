package com.aariz.sportsapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FullscreenImagePagerAdapter(
    fragmentActivity: FragmentActivity,
    private val images: List<Int>
) : FragmentStateAdapter(fragmentActivity) {
    
    override fun getItemCount(): Int = images.size
    
    override fun createFragment(position: Int): Fragment {
        return FullscreenImageFragment.newInstance(images[position])
    }
} 