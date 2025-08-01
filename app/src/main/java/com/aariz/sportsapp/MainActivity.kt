package com.aariz.sportsapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null
    private var previousFragment: Fragment? = null
    private var previousTitle: String? = null
    private var isOnHomeScreen = true
    private var isDrawerOpen = false
    

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
        setupNavigationDrawer()
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

        // Schedule navigation - with header
        binding.llNavMore.setOnClickListener {
            navigateToFragment(ScheduleFragment(), "Match Schedule")
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

    }

    fun navigateToFragment(fragment: Fragment, title: String = "CricTech") {
        if (currentFragment?.javaClass != fragment.javaClass) {
            isOnHomeScreen = false
            
            // Store current fragment as previous for back navigation
            if (currentFragment != null) {
                previousFragment = currentFragment
                previousTitle = getCurrentTitle()
            }
            
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

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when {
                    // If drawer is open, close it first
                    isDrawerOpen -> {
                        closeNavigationDrawer()
                    }
                    // If not on home screen, check if we have a previous fragment
                    !isOnHomeScreen -> {
                        if (shouldNavigateBackToPrevious()) {
                            navigateBackToPrevious()
                        } else {
                            showHomeScreen()
                        }
                    }
                    // Let the system handle the back press (exit app)
                    else -> {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        })
    }

    private fun setupHeaderNavigation() {
        binding.ivHamburgerMenu.setOnClickListener {
            if (!isOnHomeScreen) {
                // If we're not on home screen, act as back button
                if (shouldNavigateBackToPrevious()) {
                    navigateBackToPrevious()
                } else {
                    showHomeScreen()
                }
            } else {
                // If we're on home screen, act as hamburger menu
                if (isDrawerOpen) {
                    closeNavigationDrawer()
                } else {
                    openNavigationDrawer()
                }
            }
        }
    }

    private fun setupNavigationDrawer() {
        // Get navigation drawer overlay view
        val navDrawerOverlay = findViewById<View>(R.id.nav_drawer_overlay)
        val navDrawerContent = findViewById<View>(R.id.nav_drawer_content)
        val navDrawerBackground = findViewById<View>(R.id.nav_drawer_background)

        // Wait for layout to be complete before setting initial position
        navDrawerContent.post {
            // Initially set drawer content off screen (to the left)
            navDrawerContent.translationX = -navDrawerContent.width.toFloat()
        }

        // Close drawer when clicking on the background overlay
        navDrawerBackground.setOnClickListener {
            closeNavigationDrawer()
        }

        // Setup navigation item click listeners
        setupDrawerNavigationItems()
        setupExpandableSections()
        setupSearchFunctionality()
    }

    private fun setupDrawerNavigationItems() {

        // Search Bar
        findViewById<View>(R.id.nav_item_search_bar).setOnClickListener {
            closeNavigationDrawer()
        }

        // Player Comparison
        findViewById<View>(R.id.nav_item_player_comparison).setOnClickListener {
            closeNavigationDrawer()
        }

        // Browse Players
        findViewById<View>(R.id.nav_item_browse_players).setOnClickListener {
            closeNavigationDrawer()
        }

        // Browse Matches
        findViewById<View>(R.id.nav_item_browse_matches).setOnClickListener {
            closeNavigationDrawer()
        }

        // Teams
        findViewById<View>(R.id.nav_item_teams).setOnClickListener {
            closeNavigationDrawer()
        }

        // Cricket Basics - History of cricket
        findViewById<View>(R.id.nav_item_history_of_cricket).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(HistoryOfCricketFragment(), "History of Cricket")
        }

        // Cricket Basics - Rules of the game
        findViewById<View>(R.id.nav_item_rules_of_the_game).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(RulesOfTheGameFragment(), "Rules of the Game")
        }

        // Cricket Basics - Player roles
        findViewById<View>(R.id.nav_item_player_roles).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(PlayerRolesFragment(), "Player Roles")
        }

        // Cricket Basics - Match formats
        findViewById<View>(R.id.nav_item_match_formats).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(MatchFormatsFragment(), "Match Formats")
        }

        // Cricket Basics - Scoring & terms
        findViewById<View>(R.id.nav_item_scoring_terms).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(ScoringTermsFragment(), "Scoring & Terms")
        }

        // Cricket Basics - Fielding positions
        findViewById<View>(R.id.nav_item_fielding_positions).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(FieldingPositionsFragment(), "Fielding Positions")
        }

        // Cricket Basics - Strategy and tactics
        findViewById<View>(R.id.nav_item_strategy_tactics).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(StrategyAndTacticsFragment(), "Strategy and Tactics")
        }

        // Cricket Basics - Tournaments and leagues
        findViewById<View>(R.id.nav_item_tournaments_leagues).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(TournamentsAndLeaguesFragment(), "Tournaments and Leagues")
        }

        // Cricket Basics - How rankings work
        findViewById<View>(R.id.nav_item_how_rankings_work).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(HowRankingsWorkFragment(), "How Rankings Work")
        }

        // Beyond the Field - Grounds
        findViewById<View>(R.id.nav_item_grounds).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(GroundsFragment(), "Cricket Grounds")
        }

        // Beyond the Field - Umpires
        findViewById<View>(R.id.nav_item_umpires).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(UmpiresFragment(), "Umpires")
        }

        // Beyond the Field - Commentators
        findViewById<View>(R.id.nav_item_commentators).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(CommentatorsFragment(), "Commentators")
        }

        // Beyond the Field - Experts
        findViewById<View>(R.id.nav_item_experts).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(ExpertsFragment(), "Cricket Experts")
        }

        // Settings
        findViewById<View>(R.id.nav_item_settings).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(SettingsFragment(), "Settings")
        }

        // About
        findViewById<View>(R.id.nav_item_about).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(AboutFragment(), "About Us")
        }

        // Help and Support
        findViewById<View>(R.id.nav_item_help_support).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(HelpSupportFragment(), "Help and Support")
        }

        // Privacy Policy
        findViewById<View>(R.id.nav_item_privacy_policy).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(PrivacyPolicyFragment(), "Privacy Policy")
        }

        // Terms of Service
        findViewById<View>(R.id.nav_item_terms_service).setOnClickListener {
            closeNavigationDrawer()
            navigateToFragment(TermsAndConditionsFragment(), "Terms and Conditions")
        }

        // Report Issue
        findViewById<View>(R.id.nav_item_report_issue).setOnClickListener {
            closeNavigationDrawer()
        }

        // Feedback
        findViewById<View>(R.id.nav_item_feedback).setOnClickListener {
            closeNavigationDrawer()
        }
    }


    private fun openNavigationDrawer() {
        if (isDrawerOpen) return

        isDrawerOpen = true
        val navDrawerOverlay = findViewById<View>(R.id.nav_drawer_overlay)
        val navDrawerContent = findViewById<View>(R.id.nav_drawer_content)

        // Show the overlay
        navDrawerOverlay.visibility = View.VISIBLE

        // Animate the drawer sliding in from the left
        val slideInAnimator = ObjectAnimator.ofFloat(navDrawerContent, "translationX", -navDrawerContent.width.toFloat(), 0f)
        slideInAnimator.duration = 300
        slideInAnimator.start()

        // Animate the overlay fade in
        val fadeInAnimator = ObjectAnimator.ofFloat(navDrawerOverlay, "alpha", 0f, 1f)
        fadeInAnimator.duration = 300
        fadeInAnimator.start()
    }

    private fun closeNavigationDrawer() {
        if (!isDrawerOpen) return

        isDrawerOpen = false
        val navDrawerOverlay = findViewById<View>(R.id.nav_drawer_overlay)
        val navDrawerContent = findViewById<View>(R.id.nav_drawer_content)

        // Animate the drawer sliding out to the left
        val slideOutAnimator = ObjectAnimator.ofFloat(navDrawerContent, "translationX", 0f, -navDrawerContent.width.toFloat())
        slideOutAnimator.duration = 300

        // Animate the overlay fade out
        val fadeOutAnimator = ObjectAnimator.ofFloat(navDrawerOverlay, "alpha", 1f, 0f)
        fadeOutAnimator.duration = 300

        fadeOutAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                navDrawerOverlay.visibility = View.GONE
            }
        })

        slideOutAnimator.start()
        fadeOutAnimator.start()
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

    fun updateHeaderTitle(title: String) {
        binding.tvHeaderTitle.text = title
    }
    
    private fun shouldNavigateBackToPrevious(): Boolean {
        return previousFragment != null && isCricketTopicFragment(currentFragment)
    }
    
    private fun navigateBackToPrevious() {
        previousFragment?.let { fragment ->
            val title = previousTitle ?: "CricTech"
            
            // Clear previous fragment references to avoid infinite loops
            previousFragment = null
            previousTitle = null
            
            // Navigate back to the previous fragment
            navigateToFragment(fragment, title)
        }
    }
    
    private fun getCurrentTitle(): String {
        return binding.tvHeaderTitle.text.toString()
    }
    
    private fun isCricketTopicFragment(fragment: Fragment?): Boolean {
        return fragment is OriginsAndEarlyDevelopmentFragment ||
               fragment is DerivationOfNameCricketFragment ||
               fragment is GamblingAndPatronageFragment ||
               fragment is CricketExpandsBeyondEnglandFragment ||
               fragment is DevelopmentOfLawsFragment ||
               fragment is InternationalCricketBeginsFragment ||
               fragment is NineteenthCenturyCricket ||
               fragment is GrowthInColoniesFragment ||
               fragment is NationalChampionshipsFragment ||
               fragment is GrowthOfInternationalCricketFragment ||
               fragment is WorldSeriesCricketFragment ||
               fragment is ExpansionOfGameFragment ||
               fragment is T20CricketShorterFormatsFragment
    }

    private fun setupExpandableSections() {
        // Cricket Basics expandable section
        findViewById<View>(R.id.nav_item_cricket_basics).setOnClickListener {
            val cricketBasicsContainer = findViewById<View>(R.id.cricket_basics_container)
            val cricketBasicsArrow = findViewById<ImageView>(R.id.iv_cricket_basics_arrow)
            toggleExpandCollapse(cricketBasicsContainer, cricketBasicsArrow)
        }

        // Beyond the Field expandable section
        findViewById<View>(R.id.nav_item_beyond_field).setOnClickListener {
            val beyondFieldContainer = findViewById<View>(R.id.beyond_field_container)
            val beyondFieldArrow = findViewById<ImageView>(R.id.iv_beyond_field_arrow)
            toggleExpandCollapse(beyondFieldContainer, beyondFieldArrow)
        }
    }

    private fun toggleExpandCollapse(view: View, arrow: ImageView) {
        if (view.visibility == View.GONE) {
            // Expand with slide down animation
            view.visibility = View.VISIBLE
            val slideDown = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
            slideDown.duration = 200
            slideDown.start()
            
            // Rotate arrow to point up
            val rotateArrow = ObjectAnimator.ofFloat(arrow, "rotation", 0f, 180f)
            rotateArrow.duration = 200
            rotateArrow.start()
        } else {
            // Collapse with slide up animation
            val slideUp = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
            slideUp.duration = 200
            slideUp.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                }
            })
            slideUp.start()
            
            // Rotate arrow to point down
            val rotateArrow = ObjectAnimator.ofFloat(arrow, "rotation", 180f, 0f)
            rotateArrow.duration = 200
            rotateArrow.start()
        }
    }

    private fun setupSearchFunctionality() {
        val searchEditText = findViewById<EditText>(R.id.et_nav_search)
        val clearSearchButton = findViewById<ImageView>(R.id.iv_clear_search)

        // Define all searchable navigation items
        val navItems = mapOf(
            "player comparison" to findViewById<View>(R.id.nav_item_player_comparison),
            "browse players" to findViewById<View>(R.id.nav_item_browse_players),
            "browse matches" to findViewById<View>(R.id.nav_item_browse_matches),
            "teams" to findViewById<View>(R.id.nav_item_teams),
            "cricket basics" to findViewById<View>(R.id.nav_item_cricket_basics),
            "history of cricket" to findViewById<View>(R.id.nav_item_history_of_cricket),
            "rules of the game" to findViewById<View>(R.id.nav_item_rules_of_the_game),
            "player roles" to findViewById<View>(R.id.nav_item_player_roles),
            "match formats" to findViewById<View>(R.id.nav_item_match_formats),
            "scoring terms" to findViewById<View>(R.id.nav_item_scoring_terms),
            "fielding positions" to findViewById<View>(R.id.nav_item_fielding_positions),
            "strategy tactics" to findViewById<View>(R.id.nav_item_strategy_tactics),
            "tournaments leagues" to findViewById<View>(R.id.nav_item_tournaments_leagues),
            "rankings work" to findViewById<View>(R.id.nav_item_how_rankings_work),
            "beyond field" to findViewById<View>(R.id.nav_item_beyond_field),
            "inside cricket" to findViewById<View>(R.id.nav_item_beyond_field),
            "grounds" to findViewById<View>(R.id.nav_item_grounds),
            "umpires" to findViewById<View>(R.id.nav_item_umpires),
            "commentators" to findViewById<View>(R.id.nav_item_commentators),
            "experts" to findViewById<View>(R.id.nav_item_experts),
            "settings" to findViewById<View>(R.id.nav_item_settings),
            "about" to findViewById<View>(R.id.nav_item_about),
            "help support" to findViewById<View>(R.id.nav_item_help_support),
            "privacy policy" to findViewById<View>(R.id.nav_item_privacy_policy),
            "terms service" to findViewById<View>(R.id.nav_item_terms_service),
            "report issue" to findViewById<View>(R.id.nav_item_report_issue),
            "feedback" to findViewById<View>(R.id.nav_item_feedback)
        )

        val expandableContainers = mapOf(
            "cricket basics" to findViewById<View>(R.id.cricket_basics_container),
            "beyond field" to findViewById<View>(R.id.beyond_field_container)
        )

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase().trim()
                
                // Show/hide clear button
                clearSearchButton.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE
                
                if (query.isEmpty()) {
                    // Show all items when search is empty
                    showAllNavItems(navItems, expandableContainers)
                } else {
                    // Filter items based on search query
                    filterNavItems(query, navItems, expandableContainers)
                }
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })

        // Clear search functionality
        clearSearchButton.setOnClickListener {
            searchEditText.text.clear()
            searchEditText.clearFocus()
        }
    }

    private fun showAllNavItems(navItems: Map<String, View>, expandableContainers: Map<String, View>) {
        // Show all navigation items
        navItems.values.forEach { it.visibility = View.VISIBLE }
        
        // Hide expandable containers (collapsed state)
        expandableContainers.values.forEach { it.visibility = View.GONE }
        
        // Reset arrow rotations
        findViewById<ImageView>(R.id.iv_cricket_basics_arrow).rotation = 0f
        findViewById<ImageView>(R.id.iv_beyond_field_arrow).rotation = 0f
    }

    private fun filterNavItems(query: String, navItems: Map<String, View>, expandableContainers: Map<String, View>) {
        var hasVisibleItems = false
        var shouldExpandCricketBasics = false
        var shouldExpandBeyondField = false
        
        navItems.forEach { (itemName, itemView) ->
            val isVisible = itemName.contains(query)
            itemView.visibility = if (isVisible) View.VISIBLE else View.GONE
            
            if (isVisible) {
                hasVisibleItems = true
                
                // Check if we need to expand containers
                when {
                    itemName in listOf("history of cricket", "rules of the game", "player roles", 
                                      "match formats", "scoring terms", "fielding positions", 
                                      "strategy tactics", "tournaments leagues", "rankings work") -> {
                        shouldExpandCricketBasics = true
                    }
                    itemName in listOf("grounds", "umpires", "commentators", "experts") -> {
                        shouldExpandBeyondField = true
                    }
                }
            }
        }
        
        // Handle expandable containers
        val cricketBasicsContainer = expandableContainers["cricket basics"]
        val beyondFieldContainer = expandableContainers["beyond field"]
        val cricketBasicsArrow = findViewById<ImageView>(R.id.iv_cricket_basics_arrow)
        val beyondFieldArrow = findViewById<ImageView>(R.id.iv_beyond_field_arrow)
        
        // Show/hide and expand/collapse Cricket Basics
        if (shouldExpandCricketBasics) {
            cricketBasicsContainer?.visibility = View.VISIBLE
            cricketBasicsArrow.rotation = 180f
        } else {
            cricketBasicsContainer?.visibility = View.GONE
            cricketBasicsArrow.rotation = 0f
        }
        
        // Show/hide and expand/collapse Beyond Field
        if (shouldExpandBeyondField) {
            beyondFieldContainer?.visibility = View.VISIBLE
            beyondFieldArrow.rotation = 180f
        } else {
            beyondFieldContainer?.visibility = View.GONE
            beyondFieldArrow.rotation = 0f
        }
    }
}
