package com.aariz.sportsapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import androidx.core.view.isVisible

class AusT20Fragment : Fragment() {

    // Filter UI components
    private lateinit var teamsHeaderLayout: LinearLayout
    private lateinit var formatHeaderLayout: LinearLayout
    private lateinit var teamsHeader: TextView
    private lateinit var formatHeader: TextView
    private lateinit var teamsDropdown: LinearLayout
    private lateinit var formatDropdown: LinearLayout
    private lateinit var teamsDropdownArrow: ImageView
    private lateinit var formatDropdownArrow: ImageView
    private lateinit var filterActionsLayout: LinearLayout
    private lateinit var clearFiltersBtn: MaterialButton

    // Track current selections
    private var selectedTeam: String? = null
    private var selectedFormat: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("AusT20Fragment", "onCreateView called - loading aust20.xml")
        val view = inflater.inflate(R.layout.aust20, container, false)

        initViews(view)
        setupClickListeners(view)

        return view
    }

    override fun onResume() {
        super.onResume()
        // Reset filters when returning to this fragment
        resetFiltersOnReturn()
    }

    private fun initViews(view: View) {
        try {
            // Filter header layouts
            teamsHeaderLayout = view.findViewById(R.id.ll_teams_header)
            formatHeaderLayout = view.findViewById(R.id.ll_format_header)

            // Filter headers
            teamsHeader = view.findViewById(R.id.tv_teams_header)
            formatHeader = view.findViewById(R.id.tv_format_header)

            // Dropdowns
            teamsDropdown = view.findViewById(R.id.ll_teams_dropdown)
            formatDropdown = view.findViewById(R.id.ll_format_dropdown)

            // Dropdown arrows
            teamsDropdownArrow = view.findViewById(R.id.iv_teams_dropdown_arrow)
            formatDropdownArrow = view.findViewById(R.id.iv_format_dropdown_arrow)

            // Filter actions
            filterActionsLayout = view.findViewById(R.id.ll_filter_actions)
            clearFiltersBtn = view.findViewById(R.id.btn_clear_filters)

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("AusT20Fragment", "Error initializing views", e)
        }
    }

    private fun setupClickListeners(view: View) {
        // Teams header layout click
        teamsHeaderLayout.setOnClickListener {
            toggleDropdown(teamsDropdown, formatDropdown, teamsDropdownArrow, true)
        }

        // Format header layout click
        formatHeaderLayout.setOnClickListener {
            toggleDropdown(formatDropdown, teamsDropdown, formatDropdownArrow, false)
        }

        // Teams dropdown item clicks
        view.findViewById<LinearLayout>(R.id.ll_india)?.setOnClickListener { selectTeam("India") }
        view.findViewById<LinearLayout>(R.id.ll_australia)?.setOnClickListener { selectTeam("Australia") }
        view.findViewById<LinearLayout>(R.id.ll_england)?.setOnClickListener { selectTeam("England") }
        view.findViewById<LinearLayout>(R.id.ll_newzealand)?.setOnClickListener { selectTeam("New Zealand") }
        view.findViewById<LinearLayout>(R.id.ll_westindies)?.setOnClickListener { selectTeam("West Indies") }

        // Format dropdown item clicks
        view.findViewById<LinearLayout>(R.id.ll_test)?.setOnClickListener { selectFormat("Test") }
        view.findViewById<LinearLayout>(R.id.ll_odi)?.setOnClickListener { selectFormat("ODI") }
        view.findViewById<LinearLayout>(R.id.ll_t20)?.setOnClickListener { selectFormat("T20Is") }

        // Clear filters button
        clearFiltersBtn.setOnClickListener {
            clearAllFilters()
        }

        // Existing card click listeners
        val t20View1: TextView = view.findViewById(R.id.aus_t20_view1)
        val t20View2: TextView = view.findViewById(R.id.aus_t20_view2)

        // Set click listener for aus_t20_view1 to navigate to T20 World Cup 2021
        t20View1.setOnClickListener {
            Log.d("AusT20Fragment", "Clicked - T20 World Cup 2021")
            val mainActivity = activity as? MainActivity
            if (mainActivity != null && isAdded && !isDetached) {
                mainActivity.navigateToFragment(T20wc2021Fragment(), "T20 World Cup 2021")
            }
        }

        // Set click listener for aus_t20_view2 to navigate to Australia vs England - T20 Series
        t20View2.setOnClickListener {
            Log.d("AusT20Fragment", "Clicked - Australia vs England - T20 Series")
            val mainActivity = activity as? MainActivity
            if (mainActivity != null && isAdded && !isDetached) {
                mainActivity.navigateToFragment(AusEngT20Fragment(), "Australia vs England - T20 Series")
            }
        }
    }

    private fun toggleDropdown(
        targetDropdown: LinearLayout,
        otherDropdown: LinearLayout,
        arrow: ImageView,
        isTeamsDropdown: Boolean
    ) {
        // Close other dropdown first
        if (otherDropdown.visibility == View.VISIBLE) {
            animateDropdownClose(otherDropdown)
            rotateArrow(if (isTeamsDropdown) formatDropdownArrow else teamsDropdownArrow, false)
        }

        // Toggle target dropdown
        if (targetDropdown.visibility == View.VISIBLE) {
            animateDropdownClose(targetDropdown)
            rotateArrow(arrow, false)
        } else {
            animateDropdownOpen(targetDropdown)
            rotateArrow(arrow, true)
        }
    }

    private fun animateDropdownOpen(dropdown: LinearLayout) {
        dropdown.visibility = View.VISIBLE
        dropdown.alpha = 0f
        dropdown.translationY = -10f
        dropdown.scaleY = 0.95f

        dropdown.animate()
            .alpha(1f)
            .translationY(0f)
            .scaleY(1f)
            .setDuration(200)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    private fun animateDropdownClose(dropdown: LinearLayout) {
        dropdown.animate()
            .alpha(0f)
            .translationY(-10f)
            .scaleY(0.95f)
            .setDuration(150)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                dropdown.visibility = View.GONE
                dropdown.alpha = 1f
                dropdown.translationY = 0f
                dropdown.scaleY = 1f
            }
            .start()
    }

    private fun rotateArrow(arrow: ImageView, isExpanded: Boolean) {
        val rotation = if (isExpanded) 180f else 0f
        ObjectAnimator.ofFloat(arrow, "rotation", rotation).apply {
            duration = 200
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    private fun selectTeam(team: String) {
        teamsHeader.text = team
        animateDropdownClose(teamsDropdown)
        rotateArrow(teamsDropdownArrow, false)
        selectedTeam = team
        updateFilterActions()
        checkForNavigation()
    }

    private fun selectFormat(format: String) {
        formatHeader.text = format
        animateDropdownClose(formatDropdown)
        rotateArrow(formatDropdownArrow, false)
        selectedFormat = format
        updateFilterActions()
        checkForNavigation()
    }

    private fun clearAllFilters() {
        selectedTeam = null
        selectedFormat = null
        teamsHeader.text = "Select Team"
        formatHeader.text = "Select Format"
        updateFilterActions()

        // Add a subtle animation to indicate the action
        clearFiltersBtn.animate()
            .scaleX(0.9f)
            .scaleY(0.9f)
            .setDuration(100)
            .withEndAction {
                clearFiltersBtn.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .start()
            }
            .start()
    }

    private fun updateFilterActions() {
        val hasFilters = selectedTeam != null || selectedFormat != null
        filterActionsLayout.visibility = if (hasFilters) View.VISIBLE else View.GONE

        if (hasFilters) {
            filterActionsLayout.alpha = 0f
            filterActionsLayout.animate()
                .alpha(1f)
                .setDuration(200)
                .start()
        }
    }

    private fun checkForNavigation() {
        Log.d("AusT20Fragment", "checkForNavigation: team=$selectedTeam, format=$selectedFormat")

        // Only navigate if both team and format are selected
        if (selectedTeam == null || selectedFormat == null) {
            return
        }

        // Navigation for India - Test
        if (selectedTeam == "India" && selectedFormat == "Test") {
            Log.d("AusT20Fragment", "Navigating to IndTestFragment")
            navigateToFragment(IndTestFragment(), "India - Test Matches")
        }

        // Navigation for India - ODI
        else if (selectedTeam == "India" && selectedFormat == "ODI") {
            Log.d("AusT20Fragment", "Navigating to IndodiFragment")
            navigateToFragment(IndodiFragment(), "India - ODI Matches")
        }

        // Navigation for India - T20Is
        else if (selectedTeam == "India" && selectedFormat == "T20Is") {
            Log.d("AusT20Fragment", "Navigating to IndT20Fragment")
            navigateToFragment(IndT20Fragment(), "India - T20 Matches")
        }

        // Navigation for Australia - Test
        else if (selectedTeam == "Australia" && selectedFormat == "Test") {
            Log.d("AusT20Fragment", "Navigating to AusTestFragment")
            navigateToFragment(AusTestFragment(), "Australia - Test Matches")
        }

        // Navigation for Australia - ODI
        else if (selectedTeam == "Australia" && selectedFormat == "ODI") {
            Log.d("AusT20Fragment", "Navigating to AusOdiFragment")
            navigateToFragment(AusOdiFragment(), "Australia - ODI Matches")
        }

        // Navigation for Australia - T20Is
        else if (selectedTeam == "Australia" && selectedFormat == "T20Is") {
            Log.d("AusT20Fragment", "Navigating to AusT20Fragment")
            navigateToFragment(AusT20Fragment(), "Australia - T20 Matches")
        }

        // Navigation for England - Test
        else if (selectedTeam == "England" && selectedFormat == "Test") {
            Log.d("AusT20Fragment", "Navigating to EngTestFragment")
            navigateToFragment(EngTestFragment(), "England - Test Matches")
        }

        // Navigation for England - ODI
        else if (selectedTeam == "England" && selectedFormat == "ODI") {
            Log.d("AusT20Fragment", "Navigating to EngOdiFragment")
            navigateToFragment(EngOdiFragment(), "England - ODI Matches")
        }

        // Navigation for England - T20Is
        else if (selectedTeam == "England" && selectedFormat == "T20Is") {
            Log.d("AusT20Fragment", "Navigating to EngT20iFragment")
            navigateToFragment(EngT20iFragment(), "England - T20 Matches")
        }

        // Navigation for New Zealand - Test
        else if (selectedTeam == "New Zealand" && selectedFormat == "Test") {
            Log.d("AusT20Fragment", "Navigating to NzTestFragment")
            navigateToFragment(NzTestFragment(), "New Zealand - Test Matches")
        }

        // Navigation for New Zealand - ODI
        else if (selectedTeam == "New Zealand" && selectedFormat == "ODI") {
            Log.d("AusT20Fragment", "Navigating to NzOdiFragment")
            navigateToFragment(NzOdiFragment(), "New Zealand - ODI Matches")
        }

        // Navigation for New Zealand - T20Is
        else if (selectedTeam == "New Zealand" && selectedFormat == "T20Is") {
            Log.d("AusT20Fragment", "Navigating to NzT20iFragment")
            navigateToFragment(NzT20iFragment(), "New Zealand - T20 Matches")
        }

        // Navigation for West Indies - Test
        else if (selectedTeam == "West Indies" && selectedFormat == "Test") {
            Log.d("AusT20Fragment", "Navigating to WiTestFragment")
            navigateToFragment(WiTestFragment(), "West Indies - Test Matches")
        }

        // Navigation for West Indies - ODI
        else if (selectedTeam == "West Indies" && selectedFormat == "ODI") {
            Log.d("AusT20Fragment", "Navigating to WiOdiFragment")
            navigateToFragment(WiOdiFragment(), "West Indies - ODI Matches")
        }

        // Navigation for West Indies - T20Is
        else if (selectedTeam == "West Indies" && selectedFormat == "T20Is") {
            Log.d("AusT20Fragment", "Navigating to WiT20Fragment")
            navigateToFragment(WiT20Fragment(), "West Indies - T20 Matches")
        }
    }

    private fun navigateToFragment(fragment: Fragment, title: String) {
        try {
            val mainActivity = activity as? MainActivity
            if (mainActivity != null && isAdded && !isDetached) {
                Log.d("AusT20Fragment", "Calling navigateToFragment with $title")

                // Store AusT20Fragment as the previous fragment for proper back navigation
                mainActivity.setPreviousFragment(this@AusT20Fragment, "Australia - T20 Matches")

                // Navigate to the new fragment
                mainActivity.navigateToFragment(fragment, title)
            } else {
                Log.e("AusT20Fragment", "MainActivity is null or fragment not properly attached")
            }
        } catch (e: Exception) {
            Log.e("AusT20Fragment", "Error navigating to fragment: $title", e)
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up any pending animations
        teamsDropdown.animate().cancel()
        formatDropdown.animate().cancel()
        clearFiltersBtn.animate().cancel()
        filterActionsLayout.animate().cancel()
    }

    // Add method to close dropdowns when touching outside
    private fun closeAllDropdowns() {
        if (teamsDropdown.isVisible) {
            animateDropdownClose(teamsDropdown)
            rotateArrow(teamsDropdownArrow, false)
        }
        if (formatDropdown.isVisible) {
            animateDropdownClose(formatDropdown)
            rotateArrow(formatDropdownArrow, false)
        }
    }

    // Reset filters when returning to this fragment (e.g., from back button)
    private fun resetFiltersOnReturn() {
        // Close any open dropdowns
        closeAllDropdowns()

        // Clear selections
        selectedTeam = null
        selectedFormat = null

        // Reset header text
        teamsHeader.text = "Select Team"
        formatHeader.text = "Select Format"

        // Hide filter actions
        filterActionsLayout.visibility = View.GONE
    }
}