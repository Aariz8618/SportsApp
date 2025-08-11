package com.aariz.sportsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.aariz.sportsapp.databinding.ActivityFullscreenImageBinding

class FullscreenImageActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityFullscreenImageBinding
    private lateinit var imageAdapter: FullscreenImagePagerAdapter
    private var currentPosition = 0
    
    companion object {
        private const val EXTRA_IMAGES = "extra_images"
        private const val EXTRA_POSITION = "extra_position"
        
        fun start(context: Context, images: List<Int>, position: Int = 0) {
            try {
                val intent = Intent(context, FullscreenImageActivity::class.java).apply {
                    putExtra(EXTRA_IMAGES, ArrayList(images))
                    putExtra(EXTRA_POSITION, position)
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                Log.e("FullscreenImageActivity", "Error starting activity", e)
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityFullscreenImageBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            // Hide system UI for fullscreen experience
            hideSystemUI()
            
            val images = intent.getIntegerArrayListExtra(EXTRA_IMAGES) ?: arrayListOf()
            currentPosition = intent.getIntExtra(EXTRA_POSITION, 0)
            
            if (images.isNotEmpty()) {
                setupViewPager(images)
                setupCloseButton()
            } else {
                Log.e("FullscreenImageActivity", "No images provided")
                finish()
            }
        } catch (e: Exception) {
            Log.e("FullscreenImageActivity", "Error in onCreate", e)
            finish()
        }
    }
    
    private fun setupViewPager(images: List<Int>) {
        try {
            imageAdapter = FullscreenImagePagerAdapter(this, images)
            binding.viewPager.adapter = imageAdapter
            binding.viewPager.setCurrentItem(currentPosition, false)
            
            // Configure ViewPager2 to not interfere with zoom gestures
            binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.viewPager.isUserInputEnabled = true
            
            // Handle page changes
            binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    currentPosition = position
                    updatePageIndicator(position, images.size)
                }
            })
            
            updatePageIndicator(currentPosition, images.size)
        } catch (e: Exception) {
            Log.e("FullscreenImageActivity", "Error setting up ViewPager", e)
        }
    }
    
    private fun setupCloseButton() {
        try {
            binding.btnClose.setOnClickListener {
                finish()
            }
        } catch (e: Exception) {
            Log.e("FullscreenImageActivity", "Error setting up close button", e)
        }
    }
    
    private fun updatePageIndicator(current: Int, total: Int) {
        try {
            binding.pageIndicator.text = "${current + 1} / $total"
        } catch (e: Exception) {
            Log.e("FullscreenImageActivity", "Error updating page indicator", e)
        }
    }
    
    private fun hideSystemUI() {
        try {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        } catch (e: Exception) {
            Log.e("FullscreenImageActivity", "Error hiding system UI", e)
        }
    }
    
    override fun onBackPressed() {
        super.onBackPressed()
    }
} 