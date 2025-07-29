package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class GalleryFragment : Fragment() {

    private lateinit var teamsHeaderLayout: LinearLayout
    private lateinit var formatHeaderLayout: LinearLayout
    private lateinit var teamsHeader: TextView
    private lateinit var formatHeader: TextView
    private lateinit var teamsDropdown: LinearLayout
    private lateinit var formatDropdown: LinearLayout
    
    // Track current selections
    private var selectedTeam: String? = null
    private var selectedFormat: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        
        initViews(view)
        setupClickListeners(view)
        
        return view
    }

    private fun initViews(view: View) {
        try {
            teamsHeaderLayout = view.findViewById(R.id.ll_teams_header)
            formatHeaderLayout = view.findViewById(R.id.ll_format_header)
            teamsHeader = view.findViewById(R.id.tv_teams_header)
            formatHeader = view.findViewById(R.id.tv_format_header)
            teamsDropdown = view.findViewById(R.id.ll_teams_dropdown)
            formatDropdown = view.findViewById(R.id.ll_format_dropdown)
        } catch (e: Exception) {
            e.printStackTrace()
            // Log which views couldn't be found
        }
    }

    private fun setupClickListeners(view: View) {
        // Teams header layout click
        teamsHeaderLayout.setOnClickListener {
            if (teamsDropdown.visibility == View.VISIBLE) {
                teamsDropdown.visibility = View.GONE
            } else {
                teamsDropdown.visibility = View.VISIBLE
                formatDropdown.visibility = View.GONE // Hide format dropdown
            }
        }

        // Format header layout click
        formatHeaderLayout.setOnClickListener {
            if (formatDropdown.visibility == View.VISIBLE) {
                formatDropdown.visibility = View.GONE
            } else {
                formatDropdown.visibility = View.VISIBLE
                teamsDropdown.visibility = View.GONE // Hide teams dropdown
            }
        }

        // Teams dropdown item clicks
        view.findViewById<LinearLayout>(R.id.tv_india)?.setOnClickListener {
            selectTeam("India")
        }
        view.findViewById<LinearLayout>(R.id.tv_australia)?.setOnClickListener {
            selectTeam("Australia")
        }
        view.findViewById<LinearLayout>(R.id.tv_england)?.setOnClickListener {
            selectTeam("England")
        }
        view.findViewById<LinearLayout>(R.id.tv_newzealand)?.setOnClickListener {
            selectTeam("New Zealand")
        }
        view.findViewById<LinearLayout>(R.id.tv_westindies)?.setOnClickListener {
            selectTeam("West Indies")
        }

        // Format dropdown item clicks
        view.findViewById<TextView>(R.id.tv_test)?.setOnClickListener {
            selectFormat("Test")
        }
        view.findViewById<TextView>(R.id.tv_odi)?.setOnClickListener {
            selectFormat("ODI")
        }
        view.findViewById<TextView>(R.id.tv_t20)?.setOnClickListener {
            selectFormat("T20Is")
        }
    }

    private fun selectTeam(team: String) {
        teamsHeader.text = team
        teamsDropdown.visibility = View.GONE
        selectedTeam = team
        checkForNavigation()
    }

    private fun selectFormat(format: String) {
        formatHeader.text = format
        formatDropdown.visibility = View.GONE
        selectedFormat = format
        checkForNavigation()
    }
    
    private fun checkForNavigation() {
        // Add null safety checks
        if (selectedTeam == "India" && selectedFormat == "Test") {
            try {
                // Navigate to IndTestFragment via MainActivity
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(IndTestFragment(), "India - Test Matches")
                }
            } catch (e: Exception) {
                // Log error but don't crash
                e.printStackTrace()
            }
        }
    }
}
