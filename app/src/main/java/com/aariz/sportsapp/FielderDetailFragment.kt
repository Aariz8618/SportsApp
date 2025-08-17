package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentFielderDetailBinding

class FielderDetailFragment : Fragment() {

    private var _binding: FragmentFielderDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFielderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val positionName = arguments?.getString(ARG_POSITION_NAME) ?: "Unknown Position"
        val playerId = arguments?.getString(ARG_PLAYER_ID)

        binding.tvPositionName.text = positionName
        binding.tvPositionSubtitle.text = if (!playerId.isNullOrEmpty()) {
            "Mapped Player ID: $playerId"
        } else {
            "No player mapped to this position"
        }

        // TODO: Replace placeholder content with actual position details and tips
        binding.tvPositionDetails.text =
            "Details about $positionName will appear here. Add description, typical fielding situations, and coaching tips."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION_NAME = "position_name"
        const val ARG_PLAYER_ID = "player_id"
    }
}
