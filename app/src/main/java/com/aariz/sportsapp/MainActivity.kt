package com.aariz.sportsapp

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null
    private var isOnHomeScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Show home screen by default
        showHomeScreen()

        setupBottomNavigation()
        setupViewMoreClickListeners()
        setupBackPressHandler()
        setupHeaderNavigation()
    }

    private fun setupBottomNavigation() {
        // Home navigation
        binding.llNavHome.setOnClickListener {
            showHomeScreen()
        }

        // News navigation - with header
        binding.llNavNews.setOnClickListener {
            navigateToFragment(NewsFragment(), "Cricket News")
        }

        // Rankings navigation - with header
        binding.llNavRankings.setOnClickListener {
            navigateToFragment(RankingsFragment(), "Team Rankings")
        }

        // More navigation - with header
        binding.llNavMore.setOnClickListener {
            navigateToFragment(MoreFragment(), "More Options")
        }
    }

    private fun setupViewMoreClickListeners() {
        // Gallery "View More" click listener
        binding.tvGalleryViewMore.setOnClickListener {
            navigateToFragment(GalleryFragment(), "Photo Gallery")
        }

        // Highlights "View More" click listener
        binding.tvHighlightsViewMore.setOnClickListener {
            navigateToFragment(HighlightsFragment(), "Match Highlights")
        }

        // News "View More" click listener
        binding.tvNewsViewMore.setOnClickListener {
            navigateToFragment(NewsFragment(), "Cricket News")
        }

        // Memes "View More" click listener
        binding.tvMemesViewMore.setOnClickListener {
            navigateToFragment(MemesFragment(), "Cricket Memes")
        }
    }

    fun showHomeScreen() {
        isOnHomeScreen = true
        currentFragment = null

        // Show home content and header
        binding.homeContent.visibility = android.view.View.VISIBLE
        binding.fragmentContainer.visibility = android.view.View.GONE
        binding.mainHeader.visibility = android.view.View.VISIBLE

        // Show hamburger menu, hide back arrow
        updateHeaderIcon(showBackArrow = false)

        // Reset header title to app name
        updateHeaderTitle("CricTech")

        // Clear any fragment from container
        supportFragmentManager.fragments.forEach { fragment ->
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    fun navigateToFragment(fragment: Fragment, title: String = "CricTech") {
        if (currentFragment?.javaClass != fragment.javaClass) {
            isOnHomeScreen = false
            currentFragment = fragment

            // Hide home content, show fragment container
            binding.homeContent.visibility = android.view.View.GONE
            binding.fragmentContainer.visibility = android.view.View.VISIBLE
            binding.mainHeader.visibility = android.view.View.VISIBLE

            // Show back arrow instead of hamburger menu
            updateHeaderIcon(showBackArrow = true)

            // Update header title
            updateHeaderTitle(title)

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    // Keep this method for future use if needed, but currently not using it
    fun navigateToFragmentNoHeader(fragment: Fragment) {
        if (currentFragment?.javaClass != fragment.javaClass) {
            isOnHomeScreen = false
            currentFragment = fragment

            // Hide home content and header, show fragment container
            binding.homeContent.visibility = android.view.View.GONE
            binding.fragmentContainer.visibility = android.view.View.VISIBLE
            binding.mainHeader.visibility = android.view.View.GONE

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // If not on home screen, go back to home
                if (!isOnHomeScreen) {
                    showHomeScreen()
                } else {
                    // Let the system handle the back press (exit app)
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun setupHeaderNavigation() {
        binding.ivHamburgerMenu.setOnClickListener {
            if (!isOnHomeScreen) {
                // If we're not on home screen, act as back button
                showHomeScreen()
            } else {
                // If we're on home screen, act as hamburger menu (you can add menu functionality here)
                // For now, we'll just do nothing or you can implement a drawer menu
            }
        }
    }

    private fun updateHeaderIcon(showBackArrow: Boolean) {
        if (showBackArrow) {
            binding.ivHamburgerMenu.setImageResource(R.drawable.ic_arrow_back)
            binding.ivHamburgerMenu.contentDescription = "Back"
        } else {
            binding.ivHamburgerMenu.setImageResource(R.drawable.ic_menu)
            binding.ivHamburgerMenu.contentDescription = "Menu"
        }
    }

    private fun updateHeaderTitle(title: String) {
        binding.tvHeaderTitle.text = title
    }
}