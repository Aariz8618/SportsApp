package com.aariz.sportsapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentPlayerDetailBinding
import com.aariz.sportsapp.model.Player
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import java.text.SimpleDateFormat
import java.util.*

class PlayerDetailFragment : Fragment() {

    private var _binding: FragmentPlayerDetailBinding? = null
    private val binding get() = _binding!!

    private var player: Player? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            player = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable(ARG_PLAYER, Player::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getSerializable(ARG_PLAYER) as? Player
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        player?.let { displayPlayerDetails(it) }
    }

    private fun displayPlayerDetails(player: Player) {
        binding.apply {
            // Player basic info
            tvPlayerName.text = player.fullName.ifEmpty { "${player.firstName} ${player.lastName}" }
            tvPlayerCountry.text = player.country?.name ?: "Unknown Country"
            tvPlayerPosition.text = player.position?.name ?: "Unknown Position"
            
            // Player image
            if (!player.imagePath.isNullOrBlank()) {
                Glide.with(requireContext())
                    .load(player.imagePath)
                    .transform(CircleCrop())
                    .placeholder(R.drawable.ic_team_placeholder)
                    .error(R.drawable.ic_team_placeholder)
                    .into(ivPlayerImage)
            } else {
                ivPlayerImage.setImageResource(R.drawable.ic_team_placeholder)
            }
            
            // Player details
            tvPlayerFirstName.text = player.firstName
            tvPlayerLastName.text = player.lastName
            tvPlayerGender.text = player.gender ?: "Not specified"
            
            // Date of birth
            if (!player.dateOfBirth.isNullOrEmpty()) {
                try {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    val date = inputFormat.parse(player.dateOfBirth)
                    tvPlayerDob.text = outputFormat.format(date ?: Date())
                } catch (e: Exception) {
                    tvPlayerDob.text = player.dateOfBirth
                }
            } else {
                tvPlayerDob.text = "Not available"
            }
            
            // Playing style
            val battingStyle = player.battingStyle?.takeIf { it.isNotBlank() } ?: "Not specified"
            val bowlingStyle = player.bowlingStyle?.takeIf { it.isNotBlank() } ?: "Not specified"
            tvBattingStyle.text = battingStyle
            tvBowlingStyle.text = bowlingStyle
            
            // Career statistics
            val career = player.career?.firstOrNull()
            if (career != null) {
                layoutCareerStats.visibility = View.VISIBLE
                tvNoStats.visibility = View.GONE
                
                displayBattingStats(career.batting)
                displayBowlingStats(career.bowling)
            } else {
                layoutCareerStats.visibility = View.GONE
                tvNoStats.visibility = View.VISIBLE
            }
        }
    }
    
    private fun displayBattingStats(batting: com.aariz.sportsapp.model.CareerBatting?) {
        binding.apply {
            if (batting != null) {
                layoutBattingStats.visibility = View.VISIBLE
                
                tvMatches.text = batting.matches?.toString() ?: "0"
                tvInnings.text = batting.innings?.toString() ?: "0"
                tvRunsScored.text = batting.runsScored?.toString() ?: "0"
                tvNotOuts.text = batting.notOuts?.toString() ?: "0"
                tvHighestScore.text = batting.highestInningScore?.toString() ?: "0"
                tvBattingAverage.text = batting.average?.let { String.format("%.2f", it) } ?: "0.00"
                tvStrikeRate.text = batting.strikeRate?.let { String.format("%.2f", it) } ?: "0.00"
                tvBallsFaced.text = batting.ballsFaced?.toString() ?: "0"
                tvFours.text = batting.fours?.toString() ?: "0"
                tvSixes.text = batting.sixes?.toString() ?: "0"
            } else {
                layoutBattingStats.visibility = View.GONE
            }
        }
    }
    
    private fun displayBowlingStats(bowling: com.aariz.sportsapp.model.CareerBowling?) {
        binding.apply {
            if (bowling != null) {
                layoutBowlingStats.visibility = View.VISIBLE
                
                tvBowlingMatches.text = bowling.matches?.toString() ?: "0"
                tvBowlingInnings.text = bowling.innings?.toString() ?: "0"
                tvOvers.text = bowling.overs?.toString() ?: "0.0"
                tvMaidens.text = bowling.maidens?.toString() ?: "0"
                tvRunsConceded.text = bowling.runsConceded?.toString() ?: "0"
                tvWickets.text = bowling.wickets?.toString() ?: "0"
                tvBowlingAverage.text = bowling.average?.let { String.format("%.2f", it) } ?: "0.00"
                tvEconomyRate.text = bowling.economyRate?.let { String.format("%.2f", it) } ?: "0.00"
                tvBowlingStrikeRate.text = bowling.strikeRate?.let { String.format("%.2f", it) } ?: "0.00"
            } else {
                layoutBowlingStats.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PLAYER = "player"

        fun newInstance(player: Player): PlayerDetailFragment {
            return PlayerDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PLAYER, player)
                }
            }
        }
    }
}
