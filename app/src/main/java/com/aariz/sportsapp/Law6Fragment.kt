package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentLaw6Binding

class Law6Fragment : Fragment() {

    private var _binding: FragmentLaw6Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaw6Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Layout content is already defined in XML
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
