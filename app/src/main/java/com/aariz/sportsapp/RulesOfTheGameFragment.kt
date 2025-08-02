package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        setupLawClickListeners()
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
