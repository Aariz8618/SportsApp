package com.aariz.sportsapp

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null
    private var previousFragment: Fragment? = null
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
        binding.cvNewsCard.setOnClickListener {
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
        try {
            if (currentFragment?.javaClass != fragment.javaClass) {
                isOnHomeScreen = false

                // Store the previous fragment before updating current
                previousFragment = currentFragment
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
                    .commitAllowingStateLoss() // Use commitAllowingStateLoss to prevent crashes
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Fallback: show home screen
            showHomeScreen()
        }
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!isOnHomeScreen) {
                    handleBackNavigation()
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
                handleBackNavigation()
            } else {
                // If we're on home screen, act as hamburger menu
            }
        }
    }

    private fun handleBackNavigation() {
        val currentFragmentName = currentFragment?.javaClass?.simpleName ?: ""

        // Level 3: Specific tournament fragments - go back to their parent Level 2 fragment
        when {
            // India Test tournaments → IndTestFragment
            currentFragmentName.startsWith("Indt") && (currentFragmentName.contains("Bgt") || currentFragmentName.contains(
                "At"
            )) -> {
                navigateToFragment(IndTestFragment(), "India - Test Matches")
            }

            // India ODI specific tournaments → IndodiFragment  
            currentFragmentName == "IndodiausFragment" || currentFragmentName == "IndodiwcFragment" -> {
                navigateToFragment(IndodiFragment(), "India - ODI Matches")
            }

            // India T20 specific tournaments → IndT20Fragment
            currentFragmentName == "IndT2024wcFragment" || currentFragmentName == "IndNz2020Fragment" -> {
                navigateToFragment(IndT20Fragment(), "India - T20 Matches")
            }

            // Australia Test tournaments → AusTestFragment
            currentFragmentName == "Ashes23Fragment" || currentFragmentName == "Wtc23ausFragment" -> {
                navigateToFragment(AusTestFragment(), "Australia - Test Matches")
            }

            // Australia ODI tournaments → AusOdiFragment
            currentFragmentName == "Chappell2020Fragment" || currentFragmentName == "Iccwc2023Fragment" -> {
                navigateToFragment(AusOdiFragment(), "Australia - ODI Matches")
            }

            // Australia T20 tournaments → AusT20Fragment
            currentFragmentName == "AusEngT20Fragment" || currentFragmentName == "T20wc2021Fragment" -> {
                navigateToFragment(AusT20Fragment(), "Australia - T20 Matches")
            }

            // New Zealand Test tournaments → NzTestFragment
            currentFragmentName == "WhitewashFragment" || currentFragmentName == "Wtc21Fragment" -> {
                navigateToFragment(NzTestFragment(), "New Zealand - Test Matches")
            }

            // New Zealand ODI tournaments → NzOdiFragment
            currentFragmentName == "NzIndOdiFragment" || currentFragmentName == "NzWc15Fragment" -> {
                navigateToFragment(NzOdiFragment(), "New Zealand - ODI Matches")
            }

            // New Zealand T20 tournaments → NzT20iFragment
            currentFragmentName == "NzTwc21Fragment" || currentFragmentName == "NzIndT20Fragment" -> {
                navigateToFragment(NzT20iFragment(), "New Zealand - T20 Matches")
            }

            // West Indies Test tournaments → WiTestFragment
            currentFragmentName == "ShamarausFragment" || currentFragmentName == "Lara400Fragment" -> {
                navigateToFragment(WiTestFragment(), "West Indies - Test Matches")
            }

            // West Indies ODI tournaments → WiOdiFragment
            currentFragmentName == "Winz12Fragment" || currentFragmentName == "Wieng07Fragment" -> {
                navigateToFragment(WiOdiFragment(), "West Indies - ODI Matches")
            }

            // West Indies T20 tournaments → WiT20Fragment
            currentFragmentName == "Wiwc12Fragment" || currentFragmentName == "Wiwc16Fragment" -> {
                navigateToFragment(WiT20Fragment(), "West Indies - T20 Matches")
            }

            // England Test tournaments → EngTestFragment
            currentFragmentName == "EngAshesFragment" || currentFragmentName == "EngInd12Fragment" -> {
                navigateToFragment(EngTestFragment(), "England - Test Matches")
            }

            // England ODI tournaments → EngOdiFragment
            currentFragmentName == "EngOdiWcFragment" || currentFragmentName == "EngAusOdiFragment" -> {
                navigateToFragment(EngOdiFragment(), "England - ODI Matches")
            }

            // England T20 tournaments → EngT20iFragment
            currentFragmentName == "EngNzT20Fragment" || currentFragmentName == "EngT20WcFragment" -> {
                navigateToFragment(EngT20iFragment(), "England - T20 Matches")
            }

            // Level 2: Team/Format fragments (IndTestFragment, AusTestFragment, etc.) BUT NOT specific tournament fragments
            // These should go back to GalleryFragment
            (currentFragmentName == "IndTestFragment" || currentFragmentName == "IndodiFragment" || currentFragmentName == "IndT20Fragment" ||
                    currentFragmentName == "AusTestFragment" || currentFragmentName == "AusOdiFragment" || currentFragmentName == "AusT20Fragment" ||
                    currentFragmentName == "EngTestFragment" || currentFragmentName == "EngOdiFragment" || currentFragmentName == "EngT20iFragment" ||
                    currentFragmentName == "NzTestFragment" || currentFragmentName == "NzOdiFragment" || currentFragmentName == "NzT20iFragment" ||
                    currentFragmentName == "WiTestFragment" || currentFragmentName == "WiOdiFragment" || currentFragmentName == "WiT20Fragment") -> {
                navigateToFragment(GalleryFragment(), "Photo Gallery")
            }

            // Level 1: Other fragments go back to home
            else -> {
                showHomeScreen()
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