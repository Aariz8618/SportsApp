package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
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
            Log.e("GalleryFragment", "Error initializing views", e)
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
        view.findViewById<LinearLayout>(R.id.tv_india)?.setOnClickListener { selectTeam("India") }
        view.findViewById<LinearLayout>(R.id.tv_australia)
            ?.setOnClickListener { selectTeam("Australia") }
        view.findViewById<LinearLayout>(R.id.tv_england)
            ?.setOnClickListener { selectTeam("England") }
        view.findViewById<LinearLayout>(R.id.tv_newzealand)
            ?.setOnClickListener { selectTeam("New Zealand") }
        view.findViewById<LinearLayout>(R.id.tv_westindies)
            ?.setOnClickListener { selectTeam("West Indies") }

        // Format dropdown item clicks
        view.findViewById<TextView>(R.id.tv_test)?.setOnClickListener { selectFormat("Test") }
        view.findViewById<TextView>(R.id.tv_odi)?.setOnClickListener { selectFormat("ODI") }
        view.findViewById<TextView>(R.id.tv_t20)?.setOnClickListener { selectFormat("T20Is") }

        // Champions Trophy 2025 card click
        view.findViewById<TextView>(R.id.ctview)?.setOnClickListener {
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(CT25Fragment(), "Champions Trophy 2025")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to CT25Fragment", e)
                e.printStackTrace()
            }
        }

        // WTC 2025 card click
        view.findViewById<TextView>(R.id.wtcview)?.setOnClickListener {
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(WTC25Fragment(), "World Test Championship 2025")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to WTC25Fragment", e)
                e.printStackTrace()
            }
        }

        // India vs England Test card click
        view.findViewById<TextView>(R.id.inenggview)?.setOnClickListener {
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(IndEngTestFragment(), "India vs England Test Series")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to IndEngTestFragment", e)
                e.printStackTrace()
            }
        }

        // Australia vs West Indies card click
        view.findViewById<TextView>(R.id.auwiview)?.setOnClickListener {
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(AusWiTestFragment(), "Australia vs West Indies Test Series")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to AusWiTestFragment", e)
                e.printStackTrace()
            }
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
        Log.d("GalleryFragment", "checkForNavigation: team=$selectedTeam, format=$selectedFormat")

        // Navigation for India - Test
        if (selectedTeam == "India" && selectedFormat == "Test") {
            Log.d("GalleryFragment", "Navigating to IndTestFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with IndTestFragment")
                    mainActivity.navigateToFragment(IndTestFragment(), "India - Test Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to IndTestFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for India - ODI
        if (selectedTeam == "India" && selectedFormat == "ODI") {
            Log.d("GalleryFragment", "Navigating to IndodiFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with IndodiFragment")
                    mainActivity.navigateToFragment(IndodiFragment(), "India - ODI Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to IndodiFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for India - T20Is
        if (selectedTeam == "India" && selectedFormat == "T20Is") {
            Log.d("GalleryFragment", "Navigating to IndT20Fragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with IndT20Fragment")
                    mainActivity.navigateToFragment(IndT20Fragment(), "India - T20 Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to IndT20Fragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for Australia - Test
        if (selectedTeam == "Australia" && selectedFormat == "Test") {
            Log.d("GalleryFragment", "Navigating to AusTestFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with AusTestFragment")
                    mainActivity.navigateToFragment(AusTestFragment(), "Australia - Test Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to AusTestFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for Australia - ODI
        if (selectedTeam == "Australia" && selectedFormat == "ODI") {
            Log.d("GalleryFragment", "Navigating to AusOdiFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with AusOdiFragment")
                    mainActivity.navigateToFragment(AusOdiFragment(), "Australia - ODI Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to AusOdiFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for Australia - T20Is
        if (selectedTeam == "Australia" && selectedFormat == "T20Is") {
            Log.d("GalleryFragment", "Navigating to AusT20Fragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with AusT20Fragment")
                    mainActivity.navigateToFragment(AusT20Fragment(), "Australia - T20 Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to AusT20Fragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for England  - TEST
        if (selectedTeam == "England" && selectedFormat == "Test") {
            Log.d("GalleryFragment", "Navigating to EngTestFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with ENGtestFragment")
                    mainActivity.navigateToFragment(EngTestFragment(), "England Test")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to EngTestFragment", e)
                e.printStackTrace()
            }
        }


        // Navigation for England - ODI
        if (selectedTeam == "England" && selectedFormat == "ODI") {
            Log.d("GalleryFragment", "Navigating to EngOdiFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with EngOdiFragment")
                    mainActivity.navigateToFragment(EngOdiFragment(), "England- ODI Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to EngOdiFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for England - T20Is
        if (selectedTeam == "England" && selectedFormat == "T20Is") {
            Log.d("GalleryFragment", "Navigating to EngT20iFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with EngT20iFragment")
                    mainActivity.navigateToFragment(EngT20iFragment(), "England- T20 Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to EngT20iFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for New Zealand - Test
        if (selectedTeam == "New Zealand" && selectedFormat == "Test") {
            Log.d("GalleryFragment", "Navigating to NzTestFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with NzTestFragment")
                    mainActivity.navigateToFragment(NzTestFragment(), "New Zealand - Test Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to NzTestFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for New Zealand - ODI
        if (selectedTeam == "New Zealand" && selectedFormat == "ODI") {
            Log.d("GalleryFragment", "Navigating to NzOdiFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with NzOdiFragment")
                    mainActivity.navigateToFragment(NzOdiFragment(), "New Zealand - ODI Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to NzOdiFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for New Zealand - T20Is
        if (selectedTeam == "New Zealand" && selectedFormat == "T20Is") {
            Log.d("GalleryFragment", "Navigating to NzT20iFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with NzT20iFragment")
                    mainActivity.navigateToFragment(NzT20iFragment(), "New Zealand - T20 Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to NzT20iFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for West Indies - Test
        if (selectedTeam == "West Indies" && selectedFormat == "Test") {
            Log.d("GalleryFragment", "Navigating to WiTestFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with WiTestFragment")
                    mainActivity.navigateToFragment(WiTestFragment(), "West Indies - Test Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to WiTestFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for West Indies - ODI
        if (selectedTeam == "West Indies" && selectedFormat == "ODI") {
            Log.d("GalleryFragment", "Navigating to WiOdiFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with WiOdiFragment")
                    mainActivity.navigateToFragment(WiOdiFragment(), "West Indies - ODI Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to WiOdiFragment", e)
                e.printStackTrace()
            }
        }

        // Navigation for West Indies - T20Is
        if (selectedTeam == "West Indies" && selectedFormat == "T20Is") {
            Log.d("GalleryFragment", "Navigating to WiT20Fragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("GalleryFragment", "Calling navigateToFragment with WiT20Fragment")
                    mainActivity.navigateToFragment(WiT20Fragment(), "West Indies - T20 Matches")
                } else {
                    Log.e("GalleryFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("GalleryFragment", "Error navigating to WiT20Fragment", e)
                e.printStackTrace()
            }
        }
    }
}
