package com.aariz.sportsapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class FullscreenImageActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var pageIndicator: TextView
    private lateinit var btnClose: ImageButton

    private var currentPosition = 0
    private lateinit var imageAdapter: FullscreenImagePagerAdapter

    companion object {
        private const val TAG = "FullscreenImageActivity"
        private const val EXTRA_IMAGES = "extra_images"
        private const val EXTRA_POSITION = "extra_position"

        fun start(context: Context, images: List<Int>, position: Int = 0) {
            try {
                Log.d(TAG, "Starting FullscreenImageActivity with ${images.size} images at position $position")

                if (images.isEmpty()) {
                    Log.w(TAG, "No images provided, not starting activity")
                    return
                }

                // Validate position
                val validPosition = position.coerceIn(0, images.lastIndex)

                val intent = Intent(context, FullscreenImageActivity::class.java).apply {
                    putExtra(EXTRA_IMAGES, ArrayList(images))
                    putExtra(EXTRA_POSITION, validPosition)
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                Log.e(TAG, "Error starting FullscreenImageActivity", e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Log.d(TAG, "onCreate called")

            // Hide system UI before setting content view
            hideSystemUI()

            setContentView(R.layout.activity_fullscreen_image)

            // Initialize views first
            initViews()

            // Get data from intent
            val images = intent.getIntegerArrayListExtra(EXTRA_IMAGES) ?: arrayListOf()
            currentPosition = intent.getIntExtra(EXTRA_POSITION, 0)

            Log.d(TAG, "Received ${images.size} images, starting position: $currentPosition")

            if (images.isEmpty()) {
                Log.e(TAG, "No images received, finishing activity")
                finish()
                return
            }

            // Validate and adjust position
            currentPosition = currentPosition.coerceIn(0, images.lastIndex)

            // Set up the adapter and viewpager
            setupViewPager(images)

            // Set up UI interactions
            setupClickListeners()

        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
            finish()
        }
    }

    private fun initViews() {
        try {
            viewPager = findViewById(R.id.viewPager)
            pageIndicator = findViewById(R.id.pageIndicator)
            btnClose = findViewById(R.id.btnClose)
            Log.d(TAG, "Views initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing views", e)
            throw e
        }
    }

    private fun setupViewPager(images: List<Int>) {
        try {
            // Create and set adapter
            imageAdapter = FullscreenImagePagerAdapter(this, images)
            viewPager.adapter = imageAdapter

            // Set current item
            viewPager.setCurrentItem(currentPosition, false)
            updatePageIndicator(currentPosition, images.size)

            // Handle page changes
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    currentPosition = position
                    updatePageIndicator(position, images.size)
                    Log.d(TAG, "Page selected: $position")
                }
            })

            Log.d(TAG, "ViewPager setup completed")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up ViewPager", e)
            throw e
        }
    }

    private fun setupClickListeners() {
        try {
            // Close button
            btnClose.setOnClickListener {
                Log.d(TAG, "Close button clicked")
                finish()
            }

            // Back press handling
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Back pressed")
                    finish()
                }
            })

            Log.d(TAG, "Click listeners setup completed")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up click listeners", e)
        }
    }

    private fun updatePageIndicator(current: Int, total: Int) {
        try {
            if (total > 1) {
                pageIndicator.text = "${current + 1} / $total"
                pageIndicator.visibility = View.VISIBLE
            } else {
                pageIndicator.visibility = View.GONE
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating page indicator", e)
        }
    }

    private fun hideSystemUI() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
                window.insetsController?.let { controller ->
                    controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                    controller.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            } else {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            }
            Log.d(TAG, "System UI hidden")
        } catch (e: Exception) {
            Log.e(TAG, "Error hiding system UI", e)
        }
    }

    override fun onResume() {
        super.onResume()
        // Ensure fullscreen mode is maintained
        hideSystemUI()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Activity destroyed")
    }
}