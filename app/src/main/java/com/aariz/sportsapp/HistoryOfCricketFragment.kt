package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentHistoryOfCricketBinding

class HistoryOfCricketFragment : Fragment() {

    private var _binding: FragmentHistoryOfCricketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryOfCricketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title1.setOnClickListener {
            navigateToFragment(OriginsAndEarlyDevelopmentFragment())
        }
        binding.title2.setOnClickListener {
            navigateToFragment(DerivationOfNameCricketFragment())
        }
        binding.title3.setOnClickListener {
            navigateToFragment(GamblingAndPatronageFragment())
        }
        binding.title4.setOnClickListener {
            navigateToFragment(CricketExpandsBeyondEnglandFragment())
        }
        binding.title5.setOnClickListener {
            navigateToFragment(DevelopmentOfLawsFragment())
        }
        binding.title6.setOnClickListener {
            navigateToFragment(NineteenthCenturyCricket())
        }
        binding.title7.setOnClickListener {
            navigateToFragment(InternationalCricketBeginsFragment())
        }
        binding.title8.setOnClickListener {
            navigateToFragment(GrowthInColoniesFragment())
        }
        binding.title9.setOnClickListener {
            navigateToFragment(NationalChampionshipsFragment())
        }
        binding.title10.setOnClickListener {
            navigateToFragment(GrowthOfInternationalCricketFragment())
        }
        binding.title11.setOnClickListener {
            navigateToFragment(WorldSeriesCricketFragment())
        }
        binding.title12.setOnClickListener {
            navigateToFragment(ExpansionOfGameFragment())
        }
        binding.title13.setOnClickListener {
            navigateToFragment(T20CricketShorterFormatsFragment())
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        // Get the MainActivity and use its navigation method
        val mainActivity = activity as? MainActivity
        mainActivity?.navigateToFragment(fragment, getFragmentTitle(fragment))
    }
    
    private fun getFragmentTitle(fragment: Fragment): String {
        return when (fragment) {
            is OriginsAndEarlyDevelopmentFragment -> "Origins and Early Development"
            is DerivationOfNameCricketFragment -> "Derivation of the name 'cricket'"
            is GamblingAndPatronageFragment -> "Gambling and Patronage"
            is CricketExpandsBeyondEnglandFragment -> "Cricket Expands Beyond England"
            is DevelopmentOfLawsFragment -> "Development of the Laws"
            is InternationalCricketBeginsFragment -> "International Cricket Begins"
            is NineteenthCenturyCricket -> "19th Century Cricket"
            is GrowthInColoniesFragment -> "Growth in Colonies"
            is NationalChampionshipsFragment -> "National Championships"
            is GrowthOfInternationalCricketFragment -> "Growth of International Cricket"
            is WorldSeriesCricketFragment -> "World Series Cricket"
            is ExpansionOfGameFragment -> "Expansion of the Game"
            is T20CricketShorterFormatsFragment -> "T20 Cricket and Shorter Formats"
            else -> "Cricket History"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
