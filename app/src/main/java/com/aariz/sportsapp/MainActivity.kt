package com.aariz.sportsapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.graphics.drawable.Drawable
import android.content.res.ColorStateList
import androidx.core.view.ViewCompat
import android.animation.ValueAnimator
import android.animation.ArgbEvaluator
import com.aariz.sportsapp.databinding.ActivityMainBinding
import com.aariz.sportsapp.BrowseMatchesFragment
import com.aariz.sportsapp.ScheduleFragment
import com.aariz.sportsapp.PlayersFragment
import com.google.firebase.FirebaseApp
import com.aariz.sportsapp.chat.ChatbotFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null
    private var previousFragment: Fragment? = null
    private var previousTitle: String? = null
    private var isOnHomeScreen = true
    private var isDrawerOpen = false
    private var wasNavigatedFromDrawer = false
    private var homeScorecardAdapter: com.aariz.sportsapp.adapters.HomeScorecardAdapter? = null
    private var homeScorecards: MutableList<com.aariz.sportsapp.adapters.HomeScorecardItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ensure the floating bubble overlay is drawn above everything
        binding.navBubbleOverlay.bringToFront()

        // Bring chat FAB above content and bottom nav
        binding.fabChat.bringToFront()
        binding.fabChat.setOnClickListener {
            navigateToFragment(ChatbotFragment(), title = "Cricket Chatbot", addToBackStack = true)
        }

        // Show home screen by default
        showHomeScreen()

        setupBottomNavigation()
        setupViewMoreClickListeners()
        setupHomeScorecardsRecycler()
        fetchHomeScorecards()
        setupBackPressHandler()
        setupHeaderNavigation()
        setupNavigationDrawer()
    }

    private fun setupBottomNavigation() {
        // Home navigation
        binding.llNavHome.setOnClickListener {
            animatePop(binding.ivNavHome)
            updateBottomNavSelection(SelectedTab.HOME)
            showHomeScreen()
        }

        // News navigation - with header
        binding.llNavNews.setOnClickListener {
            animatePop(binding.ivNavNews)
            updateBottomNavSelection(SelectedTab.NEWS)
            navigateToFragment(NewsFragment(), "Cricket News")
        }

        // Rankings navigation - open Test Rankings directly
        binding.llNavRankings.setOnClickListener {
            animatePop(binding.ivNavRankings)
            updateBottomNavSelection(SelectedTab.RANKINGS)
            navigateToFragment(TestRankingsFragment(), "Test Team Rankings")
        }

        // Schedule navigation - with header
        binding.llNavMore.setOnClickListener {
            animatePop(binding.ivNavSchedule)
            updateBottomNavSelection(SelectedTab.SCHEDULE)
            navigateToFragment(ScheduleFragment(), "Match Schedule")
        }
    }

    private enum class SelectedTab { HOME, NEWS, RANKINGS, SCHEDULE }

    private fun animatePop(view: View) {
        // Keep icon exactly the same size; rely on lift animation only.
        // No-op pop to retain call sites without visual scaling.
    }

    private fun updateBottomNavSelection(selected: SelectedTab) {
        val labelActive = Color.parseColor("#2563EB") // label blue
        val iconActive = Color.parseColor("#132A64")  // darker navy for icon
        val inactive = Color.parseColor("#666666") // gray

        fun setItem(tabRoot: View, icon: ImageView, label: TextView, isActive: Boolean) {
            val targetIconColor = if (isActive) iconActive else inactive
            val targetLabelColor = if (isActive) labelActive else inactive

            // Smooth color transition
            animateIconColor(icon, targetIconColor)
            label.setTextColor(targetLabelColor)
            label.paint.isFakeBoldText = isActive
            // Do not animate/resize the container itself; we use a floating overlay bubble for perfection
            tabRoot.background = null
            tabRoot.setPadding(0, 0, 0, 0)
            tabRoot.elevation = 0f
            tabRoot.translationZ = 0f
            tabRoot.translationY = 0f

            if (isActive) {
                // Keep the icon in place; show bubble behind; scale icon slightly bigger
                icon.alpha = 1f
                icon.animate().scaleX(1.18f).scaleY(1.18f)
                    .setDuration(160)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
                showActiveNavBubble(over = tabRoot, iconView = icon, bubbleColor = Color.parseColor("#FFFFFF"))
            } else {
                icon.animate().scaleX(1f).scaleY(1f)
                    .setDuration(140)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
                hideActiveNavBubble()
            }
        }

        setItem(binding.llNavHome, binding.ivNavHome, binding.tvNavHome, selected == SelectedTab.HOME)
        setItem(binding.llNavNews, binding.ivNavNews, binding.tvNavNews, selected == SelectedTab.NEWS)
        setItem(binding.llNavRankings, binding.ivNavRankings, binding.tvNavRankings, selected == SelectedTab.RANKINGS)
        setItem(binding.llNavMore, binding.ivNavSchedule, binding.tvNavSchedule, selected == SelectedTab.SCHEDULE)
    }

    private fun dpToPx(dp: Int): Int = (dp * resources.displayMetrics.density).toInt()

    private fun showActiveNavBubble(over: View, iconView: ImageView, bubbleColor: Int) {
        val bubble = binding.activeNavBubble
        val bubbleIcon = binding.ivActiveNav
        // We don't duplicate the icon; keep it in place
        bubbleIcon.visibility = View.GONE

        // Make visible and set tint color of bubble
        if (bubble.visibility != View.VISIBLE) bubble.visibility = View.VISIBLE
        ViewCompat.setBackgroundTintList(bubble, ColorStateList.valueOf(bubbleColor))

        // Center the bubble on the icon's center; no lift/popping
        bubble.post {
            val iconLoc = IntArray(2)
            val overlayLoc = IntArray(2)
            iconView.getLocationOnScreen(iconLoc)
            binding.navBubbleOverlay.getLocationOnScreen(overlayLoc)

            val centerX = iconLoc[0] + iconView.width / 2f
            val centerY = iconLoc[1] + iconView.height / 2f

            val bubbleHalf = bubble.width / 2f
            val targetX = centerX - overlayLoc[0] - bubbleHalf
            val targetY = centerY - overlayLoc[1] - bubbleHalf

            // Smoothly move bubble into place without vertical pop
            bubble.animate().translationX(targetX).setDuration(160)
                .setInterpolator(AccelerateDecelerateInterpolator()).start()
            bubble.animate().translationY(targetY).setDuration(160)
                .setInterpolator(AccelerateDecelerateInterpolator()).start()
            // Optional subtle scale/alpha for feedback
            bubble.scaleX = 0.9f
            bubble.scaleY = 0.9f
            bubble.alpha = 0.0f
            bubble.animate().scaleX(1f).scaleY(1f).alpha(1f)
                .setDuration(160).setInterpolator(AccelerateDecelerateInterpolator()).start()
        }
    }

    private fun hideActiveNavBubble() {
        val bubble = binding.activeNavBubble
        if (bubble.visibility == View.VISIBLE) {
            bubble.animate().alpha(0f).setDuration(120)
                .withEndAction {
                    bubble.visibility = View.GONE
                    bubble.alpha = 1f
                }.start()
        }
    }

    private fun animateIconColor(icon: ImageView, toColor: Int) {
        // Simplified: directly set color to avoid relying on PorterDuffColorFilter internals
        icon.setColorFilter(toColor)
    }

    private fun setupViewMoreClickListeners() {
        // Gallery "View More" click listener
        binding.tvGalleryViewMore.setOnClickListener {
            navigateToFragment(GalleryFragment(), "Photo Gallery", addToBackStack = true)
            hideBottomNavigation()
        }

        // Highlights "View More" click listener
        binding.tvHighlightsViewMore.setOnClickListener {
            navigateToFragment(HighlightsFragment(), "Match Highlights")
            hideBottomNavigation()
        }

        // News "View More" click listener
        binding.cvNewsCard.setOnClickListener {
            navigateToFragment(NewsFragment(), "Cricket News")
            hideBottomNavigation()
        }

        // Memes "View More" click listener
        binding.tvMemesViewMore.setOnClickListener {
            navigateToFragment(MemesFragment(), "Cricket Memes")
            hideBottomNavigation()
        }
    }

    private fun setupHomeScorecardsRecycler() {
        val lm = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeScorecards.layoutManager = lm
        // Pager-like snapping and equal-width cards with side peeking
        val snapHelper = androidx.recyclerview.widget.PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvHomeScorecards)
        binding.rvHomeScorecards.setPadding(dpToPx(12), 0, dpToPx(12), 0)
        binding.rvHomeScorecards.clipToPadding = false

        // Ensure visible spacing between adjacent cards
        binding.rvHomeScorecards.addItemDecoration(object : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: android.graphics.Rect,
                view: android.view.View,
                parent: androidx.recyclerview.widget.RecyclerView,
                state: androidx.recyclerview.widget.RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                val space = dpToPx(8)
                val last = (parent.adapter?.itemCount ?: 0) - 1
                outRect.left = if (position == 0) 0 else space / 2
                outRect.right = if (position == last) 0 else space / 2
            }
        })

        // Adapter and click action
        homeScorecardAdapter = com.aariz.sportsapp.adapters.HomeScorecardAdapter(
            emptyList(),
            onClick = { _ ->
                // On click of a scorecard, navigate to Not Available for now
                navigateToFragment(NotAvailableFragment(), "Not Available", addToBackStack = true)
                hideBottomNavigation()
            },
            onScheduleClick = {
                // Navigate to ScheduleFragment when user taps 'Schedule' on a card
                navigateToFragment(ScheduleFragment(), "Match Schedule", addToBackStack = true)
            }
        )
        binding.rvHomeScorecards.adapter = homeScorecardAdapter

        // Show placeholder cards immediately so home isn't blank
        val placeholders = List(5) {
            com.aariz.sportsapp.adapters.HomeScorecardItem(
                matchId = null,
                matchType = null,
                status = "",
                t1 = "",
                t2 = "",
                t1s = "",
                t2s = "",
                t1imgUrl = null,
                t2imgUrl = null,
                series = "",
                isPlaceholder = true
            )
        }
        homeScorecardAdapter?.submitList(placeholders)
    }

    private fun fetchHomeScorecards(offset: Int = 0) {
        val apiKey = "d048d0c6-efeb-4bf5-99e2-88f44cb23b82"
        val call: retrofit2.Call<com.aariz.sportsapp.models.CurrentMatchesResponse> = com.aariz.sportsapp.api.CricApiClient.apiService.getCurrentMatches(apiKey, offset)
        call.enqueue(object : retrofit2.Callback<com.aariz.sportsapp.models.CurrentMatchesResponse> {
            override fun onResponse(
                call: retrofit2.Call<com.aariz.sportsapp.models.CurrentMatchesResponse>,
                response: retrofit2.Response<com.aariz.sportsapp.models.CurrentMatchesResponse>
            ) {
                val body = response.body()
                val list = body?.data ?: emptyList()
                val items = mapMatchesToScorecards(list).toMutableList()
                homeScorecards = items
                homeScorecardAdapter?.submitList(items)

                // Fetch team images per match using scorecard endpoint only if missing
                items.forEach { item ->
                    val matchId = item.matchId
                    val needImages = item.t1imgUrl.isNullOrEmpty() || item.t2imgUrl.isNullOrEmpty()
                    if (needImages && !matchId.isNullOrBlank()) {
                        fetchTeamImagesForMatch(apiKey, matchId, item.t1, item.t2)
                    }
                }
            }

            override fun onFailure(
                call: retrofit2.Call<com.aariz.sportsapp.models.CurrentMatchesResponse>,
                t: Throwable
            ) {
                // Keep existing placeholders; optionally you can show a toast/log here
            }
        })
    }

    private fun mapMatchesToScorecards(matches: List<com.aariz.sportsapp.models.MatchData>): List<com.aariz.sportsapp.adapters.HomeScorecardItem> {
        return matches.mapNotNull { m ->
            // Prefer newer fields t1/t2 if present; else fall back to teams list
            val t1 = m.t1 ?: m.teams?.getOrNull(0)
            val t2 = m.t2 ?: m.teams?.getOrNull(1)
            if (t1 == null || t2 == null) return@mapNotNull null

            // Prefer provided score strings; else compute from innings
            val t1sRaw = m.t1s ?: getTeamScoreString(t1, m.score)
            val t2sRaw = m.t2s ?: getTeamScoreString(t2, m.score)

            // Determine batting order: the team that appears first in the score list goes on top
            val scores = m.score ?: emptyList()
            fun firstIndexFor(team: String): Int {
                val key = team.lowercase()
                return scores.indexOfFirst { sc ->
                    val inn = sc.inning?.lowercase() ?: return@indexOfFirst false
                    inn.contains(key)
                }
            }
            val idx1 = firstIndexFor(t1)
            val idx2 = firstIndexFor(t2)

            val displayT1: String
            val displayT2: String
            val displayT1s: String
            val displayT2s: String
            val displayT1img: String?
            val displayT2img: String?

            val t1img = m.t1img
            val t2img = m.t2img

            if (idx1 >= 0 && idx2 >= 0 && idx2 < idx1) {
                // Team 2 batted first -> put team2 on top
                displayT1 = t2
                displayT2 = t1
                displayT1s = t2sRaw
                displayT2s = t1sRaw
                displayT1img = t2img
                displayT2img = t1img
            } else {
                // Default or team1 batted first -> keep order
                displayT1 = t1
                displayT2 = t2
                displayT1s = t1sRaw
                displayT2s = t2sRaw
                displayT1img = t1img
                displayT2img = t2img
            }

            val seriesDisplay = m.seriesName ?: m.name ?: m.series_id
            com.aariz.sportsapp.adapters.HomeScorecardItem(
                matchId = m.id,
                matchType = m.matchType,
                status = m.status ?: "",
                t1 = displayT1,
                t2 = displayT2,
                t1s = displayT1s,
                t2s = displayT2s,
                t1imgUrl = displayT1img,
                t2imgUrl = displayT2img,
                series = seriesDisplay
            )
        }
    }

    private fun getTeamScoreString(team: String?, scores: List<com.aariz.sportsapp.models.ScoreData>?): String {
        if (team.isNullOrBlank() || scores.isNullOrEmpty()) return ""
        // Find the most recent innings for this team by matching team name in the inning field
        val entry = scores.lastOrNull { sc ->
            val inn = sc.inning?.lowercase() ?: return@lastOrNull false
            val teamKey = team.lowercase()
            inn.contains(teamKey)
        } ?: return ""
        val r = entry.r ?: return ""
        val w = entry.w ?: 0
        val o = entry.o ?: 0.0
        // Format overs: drop trailing .0
        val oversStr = if (o % 1.0 == 0.0) o.toInt().toString() else o.toString()
        // Single line: e.g. 160/6 (20)
        return "$r/$w ($oversStr)"
    }

    private fun fetchTeamImagesForMatch(apiKey: String, matchId: String, t1: String?, t2: String?) {
        val call = com.aariz.sportsapp.api.CricApiClient.apiService.getScorecard(apiKey, matchId)
        call.enqueue(object : retrofit2.Callback<com.aariz.sportsapp.models.ScorecardResponse> {
            override fun onResponse(
                call: retrofit2.Call<com.aariz.sportsapp.models.ScorecardResponse>,
                response: retrofit2.Response<com.aariz.sportsapp.models.ScorecardResponse>
            ) {
                val data = response.body()?.data
                val teamInfo = data?.teamInfo ?: emptyList()
                var t1Url: String? = null
                var t2Url: String? = null
                if (teamInfo.isNotEmpty()) {
                    // Match by shortName or name
                    teamInfo.forEach { info ->
                        val name = info.name?.lowercase()
                        val shortName = info.shortName?.lowercase()
                        if (t1 != null && (name == t1.lowercase() || shortName == t1.lowercase())) t1Url = info.img
                        if (t2 != null && (name == t2.lowercase() || shortName == t2.lowercase())) t2Url = info.img
                    }
                    // If not matched, assign first/second images as fallback
                    if (t1Url == null) t1Url = teamInfo.getOrNull(0)?.img
                    if (t2Url == null) t2Url = teamInfo.getOrNull(1)?.img
                }

                // Update the item in the list and refresh adapter
                val idx = homeScorecards.indexOfFirst { it.matchId == matchId }
                if (idx >= 0) {
                    val old = homeScorecards[idx]
                    val updated = old.copy(t1imgUrl = t1Url, t2imgUrl = t2Url)
                    homeScorecards[idx] = updated
                    homeScorecardAdapter?.submitList(homeScorecards.toList())
                }
            }

            override fun onFailure(
                call: retrofit2.Call<com.aariz.sportsapp.models.ScorecardResponse>,
                t: Throwable
            ) {
                // Ignore; placeholders will remain
            }
        })
    }

    fun showHomeScreen() {
        isOnHomeScreen = true
        currentFragment = null

        // Show home content and header
        binding.homeContent.visibility = View.VISIBLE
        binding.fragmentContainer.visibility = View.GONE
        binding.mainHeader.visibility = View.VISIBLE
        binding.fabChat.visibility = View.VISIBLE

        // Show hamburger menu, hide back arrow
        updateHeaderIcon(showBackArrow = false)

        // Reset header title to app name
        updateHeaderTitle("CricTech")
        
        // Show bottom navigation when returning to home
        showBottomNavigation()

        // Reflect selection state
        updateBottomNavSelection(SelectedTab.HOME)
    }

    fun navigateToFragment(fragment: Fragment, title: String = "CricTech", addToBackStack: Boolean = false
    ) {
        if (currentFragment?.javaClass != fragment.javaClass || addToBackStack) {
            isOnHomeScreen = false

            // Store current fragment only if we're not adding to back stack
            if (!addToBackStack && currentFragment != null) {
                previousFragment = currentFragment
                previousTitle = getCurrentTitle()
            }

            currentFragment = fragment

            binding.homeContent.visibility = View.GONE
            binding.fragmentContainer.visibility = View.VISIBLE
            // For Chatbot screen: hide black header and the global chat FAB
            if (fragment is ChatbotFragment) {
                binding.mainHeader.visibility = View.GONE
                binding.fabChat.visibility = View.GONE
                hideBottomNavigation()
            } else {
                binding.mainHeader.visibility = View.VISIBLE
                binding.fabChat.visibility = View.VISIBLE
                showBottomNavigation()
            }

            if (binding.mainHeader.visibility == View.VISIBLE) {
                updateHeaderIcon(showBackArrow = true)
                updateHeaderTitle(title)
            }

            val transaction = supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)

            // If we're navigating between filtered (cricket topic) fragments and adding to back stack,
            // pop the current filtered fragment so back goes directly to Gallery.
            if (addToBackStack && isCricketTopicFragment(currentFragment) && isCricketTopicFragment(fragment)) {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                }
            }

            if (addToBackStack) {
                transaction.addToBackStack(null)
            }

            transaction.commit()

            // Update bottom nav selection based on destination
            when (fragment) {
                is NewsFragment -> updateBottomNavSelection(SelectedTab.NEWS)
                is TestRankingsFragment, is OdiRankingsFragment, is T20IRankingsFragment ->
                    updateBottomNavSelection(SelectedTab.RANKINGS)
                is ScheduleFragment -> updateBottomNavSelection(SelectedTab.SCHEDULE)
                else -> {}
            }
        }
    }


    private fun navigateFromDrawer(fragment: Fragment, title: String) {
        wasNavigatedFromDrawer = true
        
        // Hide bottom navigation when navigating from drawer
        hideBottomNavigation()
        
        navigateToFragment(fragment, title)
    }

    private fun setupBackPressHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when {
                    // If drawer is open, close it first
                    isDrawerOpen -> {
                        closeNavigationDrawer()
                    }
                    // If not on home screen, navigate back appropriately
                    !isOnHomeScreen -> {
                        // Hide keyboard before navigating
                        hideKeyboard()

                        // Check if there are fragments in the back stack first (e.g., Chatbot)
                        if (supportFragmentManager.backStackEntryCount > 0) {
                            val willBeEmpty = supportFragmentManager.backStackEntryCount == 1
                            val wasChatbot = currentFragment is ChatbotFragment
                            // Pop from back stack (e.g., NotAvailable -> Home layout)
                            supportFragmentManager.popBackStack()
                            // If this pop leaves no fragments, restore Home layout immediately
                            if (willBeEmpty || wasChatbot) {
                                showHomeScreen()
                            }
                        } else if (shouldNavigateBackToPrevious()) {
                            // Handle custom navigation for cricket topic fragments
                            navigateBackToPrevious()
                        } else if (wasNavigatedFromDrawer) {
                            // If we came from drawer, go back to home and open drawer
                            wasNavigatedFromDrawer = false
                            showHomeScreen()
                            openNavigationDrawer()
                        } else {
                            // Default: go back to home screen instead of closing app
                            showHomeScreen()
                        }
                    }
                    // Only exit app when on home screen
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
                // Hide keyboard before navigating
                hideKeyboard()
                // First: if there are entries in the fragment back stack, pop them
                if (supportFragmentManager.backStackEntryCount > 0) {
                    val willBeEmpty = supportFragmentManager.backStackEntryCount == 1
                    supportFragmentManager.popBackStack()
                    if (willBeEmpty) {
                        showHomeScreen()
                        return@setOnClickListener
                    }
                } else if (shouldNavigateBackToPrevious()) {
                    navigateBackToPrevious()
                } else if (wasNavigatedFromDrawer) {
                    // If we came from drawer, go back to home and open drawer
                    wasNavigatedFromDrawer = false
                    showHomeScreen()
                    openNavigationDrawer()
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
            navigateFromDrawer(PlayersFragment(), "Browse Players")

        }

        // Browse Matches
        findViewById<View>(R.id.nav_item_browse_matches).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(BrowseMatchesFragment(), "Browse Matches")
        }

        // Teams
        findViewById<View>(R.id.nav_item_teams).setOnClickListener {
            closeNavigationDrawer()
        }

        // Cricket Basics - History of cricket
        findViewById<View>(R.id.nav_item_history_of_cricket).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(HistoryOfCricketFragment(), "History of Cricket")
        }

        // Cricket Basics - Rules of the game
        findViewById<View>(R.id.nav_item_rules_of_the_game).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(RulesOfTheGameFragment(), "Laws of the Game")
        }

        // Cricket Basics - Player roles
        findViewById<View>(R.id.nav_item_player_roles).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(PlayerRolesFragment(), "Player Roles")
        }

        // Cricket Basics - Match formats
        findViewById<View>(R.id.nav_item_match_formats).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(MatchFormatsFragment(), "Match Formats")
        }

        // Cricket Basics - Scoring & terms
        findViewById<View>(R.id.nav_item_scoring_terms).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(ScoringTermsFragment(), "Scoring & Terms")
        }

        // Cricket Basics - Fielding positions
        findViewById<View>(R.id.nav_item_fielding_positions).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(FieldingPositionsFragment(), "Fielding Positions")
        }

        // Cricket Basics - Strategy and tactics
        findViewById<View>(R.id.nav_item_strategy_tactics).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(StrategyAndTacticsFragment(), "Strategy and Tactics")
        }

        // Cricket Basics - Tournaments and leagues
        findViewById<View>(R.id.nav_item_tournaments_leagues).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(TournamentsAndLeaguesFragment(), "Tournaments and Leagues")
        }

        // Cricket Basics - How rankings work
        findViewById<View>(R.id.nav_item_how_rankings_work).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(HowRankingsWorkFragment(), "How Rankings Work")
        }

        // Beyond the Field - Grounds
        findViewById<View>(R.id.nav_item_grounds).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(GroundsFragment(), "Cricket Grounds")
        }

        // Beyond the Field - Umpires
        findViewById<View>(R.id.nav_item_umpires).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(UmpiresFragment(), "Umpires")
        }

        // Beyond the Field - Commentators
        findViewById<View>(R.id.nav_item_commentators).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(CommentatorsFragment(), "Commentators")
        }

        // Beyond the Field - Experts
        findViewById<View>(R.id.nav_item_experts).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(ExpertsFragment(), "Cricket Experts")
        }

        // Settings
        findViewById<View>(R.id.nav_item_settings).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(SettingsFragment(), "Settings")
        }

        // About
        findViewById<View>(R.id.nav_item_about).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(AboutFragment(), "About Us")
        }

        // Help and Support
        findViewById<View>(R.id.nav_item_help_support).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(HelpSupportFragment(), "Help and Support")
        }

        // Privacy Policy
        findViewById<View>(R.id.nav_item_privacy_policy).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(PrivacyPolicyFragment(), "Privacy Policy")
        }

        // Terms of Service
        findViewById<View>(R.id.nav_item_terms_service).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(TermsAndConditionsFragment(), "Terms and Conditions")
        }

        // Report Issue
        findViewById<View>(R.id.nav_item_report_issue).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(ReportIssueFragment(), "Report Issue")
        }

        // Feedback
        findViewById<View>(R.id.nav_item_feedback).setOnClickListener {
            closeNavigationDrawer()
            navigateFromDrawer(FeedbackFragment(), "Feedback")
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

        // Hide keyboard when closing drawer
        hideKeyboard()
        
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
               fragment is T20CricketShorterFormatsFragment ||
               fragment is Law1Fragment ||
               fragment is Law2Fragment ||
               fragment is Law3Fragment ||
               fragment is Law4Fragment ||
               fragment is Law5Fragment ||
               fragment is Law6Fragment ||
               fragment is Law7Fragment ||
               fragment is Law8Fragment ||
               fragment is Law9Fragment ||
               fragment is Law10Fragment ||
               fragment is Law11Fragment ||
               fragment is Law12Fragment ||
               fragment is Law13Fragment ||
               fragment is Law14Fragment ||
                fragment is Law15Fragment ||
                fragment is Law16Fragment ||
                fragment is Law17Fragment ||
                fragment is Law18Fragment ||
                fragment is Law19Fragment ||
                fragment is Law20Fragment ||
                fragment is Law21Fragment ||
                fragment is Law22Fragment ||
                fragment is Law23Fragment ||
                fragment is Law24Fragment ||
                fragment is Law25Fragment ||
                fragment is Law26Fragment ||
                fragment is Law27Fragment ||
                fragment is Law28Fragment ||
                fragment is Law29Fragment ||
                fragment is Law30Fragment ||
                fragment is Law31Fragment ||
                fragment is Law32Fragment ||
                fragment is Law33Fragment ||
                fragment is Law34Fragment ||
                fragment is Law35Fragment ||
                fragment is CommentatorDetailFragment ||
                fragment is IndTestFragment ||
                fragment is IndodiFragment ||
                fragment is IndT20Fragment ||
                fragment is AusTestFragment ||
                fragment is AusOdiFragment ||
                fragment is AusT20Fragment ||
                fragment is EngTestFragment ||
                fragment is EngOdiFragment ||
                fragment is EngT20iFragment ||
                fragment is NzTestFragment ||
                fragment is NzOdiFragment ||
                fragment is NzT20iFragment ||
                fragment is WiTestFragment ||
                fragment is WiOdiFragment ||
                fragment is WiT20Fragment ||
                fragment is CT25Fragment ||
                fragment is WTC25Fragment ||
                fragment is IndEngTestFragment ||
                fragment is AusWiTestFragment
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
    
    private fun hideBottomNavigation() {
        // Find the bottom navigation container and hide it
        val bottomNav = findViewById<View>(R.id.bottom_navigation)
        bottomNav?.visibility = View.GONE
    }
    
    private fun showBottomNavigation() {
        // Find the bottom navigation container and show it
        val bottomNav = findViewById<View>(R.id.bottom_navigation)
        bottomNav?.visibility = View.VISIBLE
    }
    
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun setPreviousFragment(fragment: Fragment, title: String) {
        previousFragment = fragment
        previousTitle = title
    }
}
