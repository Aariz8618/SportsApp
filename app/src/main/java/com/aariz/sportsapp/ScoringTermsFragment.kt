package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentScoringTermsBinding

class ScoringTermsFragment : Fragment() {

    private var _binding: FragmentScoringTermsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoringTermsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs()
    }

    private fun setupTabs() {
        // Set initial state - Scoring System tab selected
        selectScoringSystemTab()

        // Set click listeners
        binding.tabScoringSystem.setOnClickListener {
            selectScoringSystemTab()
        }

        binding.tabCricketTerms.setOnClickListener {
            selectCricketTermsTab()
        }
    }

    private fun selectScoringSystemTab() {
        // Update tab appearances
        binding.tabScoringSystem.setBackgroundResource(R.drawable.tab_selected_bg)
        binding.tabScoringSystem.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary))

        binding.tabCricketTerms.setBackgroundResource(R.drawable.tab_unselected_bg)
        binding.tabCricketTerms.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        // Show/hide content
        binding.scoringSystemContent.visibility = View.VISIBLE
        binding.cricketTermsContent.visibility = View.GONE
    }

    private fun selectCricketTermsTab() {
        // Update tab appearances
        binding.tabCricketTerms.setBackgroundResource(R.drawable.tab_selected_bg)
        binding.tabCricketTerms.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_primary))

        binding.tabScoringSystem.setBackgroundResource(R.drawable.tab_unselected_bg)
        binding.tabScoringSystem.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        // Show/hide content
        binding.scoringSystemContent.visibility = View.GONE
        binding.cricketTermsContent.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
