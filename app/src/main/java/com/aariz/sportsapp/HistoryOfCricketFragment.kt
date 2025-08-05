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

        binding.btnOrigins.setOnClickListener {
            navigateToFragment(OriginsAndEarlyDevelopmentFragment())
        }
        binding.btnDerivation.setOnClickListener {
            navigateToFragment(DerivationOfNameCricketFragment())
        }
        binding.btnGambling.setOnClickListener {
            navigateToFragment(GamblingAndPatronageFragment())
        }
        binding.btnExpansion.setOnClickListener {
            navigateToFragment(CricketExpandsBeyondEnglandFragment())
        }
        binding.btnLaws.setOnClickListener {
            navigateToFragment(DevelopmentOfLawsFragment())
        }
        binding.btnNineteenth.setOnClickListener {
            navigateToFragment(NineteenthCenturyCricket())
        }
        binding.btnInternational.setOnClickListener {
            navigateToFragment(InternationalCricketBeginsFragment())
        }
        binding.btnColonies.setOnClickListener {
            navigateToFragment(GrowthInColoniesFragment())
        }
        binding.btnNational.setOnClickListener {
            navigateToFragment(NationalChampionshipsFragment())
        }
        binding.btnGrowthInternational.setOnClickListener {
            navigateToFragment(GrowthOfInternationalCricketFragment())
        }
        binding.btnWorldSeries.setOnClickListener {
            navigateToFragment(WorldSeriesCricketFragment())
        }
        binding.btnGameExpansion.setOnClickListener {
            navigateToFragment(ExpansionOfGameFragment())
        }
        binding.btnT20Growth.setOnClickListener {
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
            is NineteenthCenturyCricket -> "International Cricket"
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
