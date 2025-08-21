package com.aariz.sportsapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager2.widget.ViewPager2
import com.aariz.sportsapp.databinding.ActivityFullScreenMemeBinding

class FullScreenMemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenMemeBinding
    private lateinit var fullScreenAdapter: FullScreenMemeAdapter
    private var isSystemUIVisible = false
    private var memesList: List<Meme> = emptyList()
    private var currentPosition: Int = 0

    companion object {
        const val EXTRA_MEMES_LIST = "extra_memes_list"
        const val EXTRA_CURRENT_POSITION = "extra_current_position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityFullScreenMemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        memesList = intent.getParcelableArrayListExtra<Meme>(EXTRA_MEMES_LIST) ?: emptyList()
        currentPosition = intent.getIntExtra(EXTRA_CURRENT_POSITION, 0)

        // Hide system UI for immersive experience
        setupFullScreen()
        
        // Setup ViewPager2
        setupViewPager()
        
        // Setup back press handling
        setupBackPressHandler()
        
        // Setup close button
        binding.closeButton.setOnClickListener {
            finishWithTransition()
        }

        // Setup share button
        binding.shareButton.setOnClickListener {
            shareCurrentMeme()
        }

        // Update counter
        updateCounter()
    }

    private fun setupViewPager() {
        fullScreenAdapter = FullScreenMemeAdapter(memesList) { toggleSystemUI() }
        
        binding.viewPager.apply {
            adapter = fullScreenAdapter
            setCurrentItem(currentPosition, false)
            
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPosition = position
                    updateCounter()
                }
            })
        }
    }

    private fun setupFullScreen() {
        // Make the activity full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController?.let {
            it.hide(WindowInsetsCompat.Type.systemBars())
            it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        
        // Set status bar color to transparent
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        
        // Add flags for immersive mode
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    private fun toggleSystemUI() {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        
        if (isSystemUIVisible) {
            // Hide system UI
            windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())
            binding.topButtonsContainer.visibility = View.GONE
            binding.bottomContainer.visibility = View.GONE
        } else {
            // Show system UI
            windowInsetsController?.show(WindowInsetsCompat.Type.systemBars())
            binding.topButtonsContainer.visibility = View.VISIBLE
            binding.bottomContainer.visibility = View.VISIBLE
        }
        
        isSystemUIVisible = !isSystemUIVisible
    }

    private fun updateCounter() {
        if (memesList.isNotEmpty()) {
            binding.counterText.text = "${currentPosition + 1} / ${memesList.size}"
        }
    }

    private fun shareCurrentMeme() {
        // TODO: Implement sharing functionality
        // For now, just show the current meme info
        if (currentPosition < memesList.size) {
            val currentMeme = memesList[currentPosition]
            // Share implementation would go here
        }
    }
    
    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishWithTransition()
            }
        })
    }
    
    private fun finishWithTransition() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
