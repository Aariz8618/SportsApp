package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.aariz.sportsapp.databinding.FragmentMatchFormatsBinding

class MatchFormatsFragment : Fragment() {

    private var _binding: FragmentMatchFormatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchFormatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.cardTestCricket.setOnClickListener {
            navigateToFragment(TestCricketDetailFragment())
        }

        binding.cardOdiCricket.setOnClickListener {
            navigateToFragment(ODICricketDetailFragment())
        }

        binding.cardT20Cricket.setOnClickListener {
            navigateToFragment(T20CricketDetailFragment())
        }

        binding.cardHundredCricket.setOnClickListener {
            navigateToFragment(HundredCricketDetailFragment())
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        // Hide the main content and show fragment container
        activity?.findViewById<View>(R.id.home_content)?.visibility = View.GONE
        activity?.findViewById<View>(R.id.fragment_container)?.visibility = View.VISIBLE
        
        parentFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
