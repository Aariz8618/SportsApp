package com.aariz.sportsapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentLawsOfTheGameBinding

class RulesOfTheGameFragment : Fragment() {

    private var _binding: FragmentLawsOfTheGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLawsOfTheGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFilterSpinner()
        setupSearchFunctionality()
        setupLawClickListeners()
    }

    private fun setupFilterSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filter_categories,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilter.adapter = adapter

        binding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val categories = resources.getStringArray(R.array.filter_categories)
                if (position > 0) { // Skip "FILTER LAWS" option
                    filterByCategory(categories[position])
                } else {
                    showAllLaws() // Show all laws when "FILTER LAWS" is selected
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSearchFunctionality() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterLaws(s.toString())
            }
        })

        binding.buttonSearch.setOnClickListener {
            filterLaws(binding.searchBar.text.toString())
        }
    }

    private fun filterLaws(query: String) {
        val lowercaseQuery = query.lowercase()
        
        // Define law data structure for searching
        val lawData = mapOf(
            "law_title_1" to "the players",
            "law_title_2" to "the umpires",
            "law_title_3" to "the scorers",
            "law_title_4" to "the ball",
            "law_title_5" to "the bat",
            "law_title_6" to "the pitch",
            "law_title_7" to "the creases",
            "law_title_8" to "the wickets",
            "law_title_9" to "preparation and maintenance of the playing area",
            "law_title_10" to "covering the pitch",
            "law_title_11" to "intervals",
            "law_title_12" to "start of play cessation of play",
            "law_title_13" to "innings",
            "law_title_14" to "the follow-on"
        )
        
        // Show/hide law cards based on search query
        for ((lawId, lawTitle) in lawData) {
            val lawCard = binding.root.findViewById<View>(resources.getIdentifier(lawId, "id", requireContext().packageName))?.parent?.parent as? View
            lawCard?.visibility = if (lowercaseQuery.isEmpty() || lawTitle.contains(lowercaseQuery)) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun filterByCategory(category: String) {
        // Define which laws belong to which categories based on the existing layout
        val categoryLaws = when (category) {
            "Setting Up" -> listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12) // Laws 1-12
            "Innings and Result" -> listOf(13, 14, 15, 16) // Laws 13-16 (based on existing layout colors)
            "The Over, Scoring Runs, Dead Ball and Extras" -> listOf(17, 18, 19, 20, 21, 22, 23) // Laws 17-23
            "Players, Substitutes, Runners and Practice" -> listOf(24, 25, 26, 27, 28) // Laws 24-28
            "Appeals and Dismissals" -> listOf(29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40) // Laws 29-40
            "Unfair Play" -> listOf(41, 42) // Laws 41-42
            else -> (1..42).toList() // Show all laws for any other case
        }

        // Show/hide law cards based on category
        for (i in 1..42) {
            val lawCard = binding.root.findViewById<View>(resources.getIdentifier("law_title_$i", "id", requireContext().packageName))?.parent?.parent as? View
            lawCard?.visibility = if (categoryLaws.contains(i)) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun showAllLaws() {
        // Show all law cards
        for (i in 1..42) {
            val lawCard = binding.root.findViewById<View>(resources.getIdentifier("law_title_$i", "id", requireContext().packageName))?.parent?.parent as? View
            lawCard?.visibility = View.VISIBLE
        }
    }

    private fun setupLawClickListeners() {
        // Get MainActivity instance to handle navigation
        val mainActivity = activity as? MainActivity

        // Law 1 - The Players
        binding.root.findViewById<View>(R.id.law_title_1)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law1Fragment(), "Law 1 - The Players")
        }

        // Law 2 - The Umpires
        binding.root.findViewById<View>(R.id.law_title_2)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law2Fragment(), "Law 2 - The Umpires")
        }

        // Law 3 - The Scorers
        binding.root.findViewById<View>(R.id.law_title_3)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law3Fragment(), "Law 3 - The Scorers")
        }

        // Law 4 - The Ball
        binding.root.findViewById<View>(R.id.law_title_4)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law4Fragment(), "Law 4 - The Ball")
        }

        // Law 5 - The Bat
        binding.root.findViewById<View>(R.id.law_title_5)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law5Fragment(), "Law 5 - The Bat")
        }

        // Law 6 - The Pitch
        binding.root.findViewById<View>(R.id.law_title_6)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law6Fragment(), "Law 6 - The Pitch")
        }

        // Law 7 - The Creases
        binding.root.findViewById<View>(R.id.law_title_7)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law7Fragment(), "Law 7 - The Creases")
        }

        // Law 8 - The Wickets
        binding.root.findViewById<View>(R.id.law_title_8)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law8Fragment(), "Law 8 - The Wickets")
        }

        // Law 9 - Preparation and Maintenance of the Playing Area
        binding.root.findViewById<View>(R.id.law_title_9)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law9Fragment(), "Law 9 - Playing Area")
        }

        // Law 10 - Covering the Pitch
        binding.root.findViewById<View>(R.id.law_title_10)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law10Fragment(), "Law 10 - Covering the Pitch")
        }

        // Law 11 - Intervals
        binding.root.findViewById<View>(R.id.law_title_11)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law11Fragment(), "Law 11 - Intervals")
        }

        // Law 12 - Start of Play; Cessation of Play
        binding.root.findViewById<View>(R.id.law_title_12)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law12Fragment(), "Law 12 - Start/Cessation of Play")
        }

        // Law 13 - Innings
        binding.root.findViewById<View>(R.id.law_title_13)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law13Fragment(), "Law 13 - Innings")
        }

        // Law 14 - The Follow-on
        binding.root.findViewById<View>(R.id.law_title_14)?.setOnClickListener {
            mainActivity?.navigateToFragment(Law14Fragment(), "Law 14 - The Follow-on")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
