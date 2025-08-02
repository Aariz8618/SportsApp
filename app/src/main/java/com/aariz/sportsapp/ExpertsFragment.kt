package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentExpertsBinding

class ExpertsFragment : Fragment() {

    private var _binding: FragmentExpertsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
    }

    private fun setupContent() {
        // Content is now loaded from XML layout
        // No need to set text programmatically
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
