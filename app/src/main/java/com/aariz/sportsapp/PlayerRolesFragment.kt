package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentPlayerRolesBinding

class PlayerRolesFragment : Fragment() {

    private var _binding: FragmentPlayerRolesBinding? = null
    private val binding get() = _binding!!
    
    private var currentActiveRole: String? = "batsman" // Default to batsman

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerRolesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        // Set initial state - show batsman section by default
        showRoleSection("batsman")
    }

    private fun setupClickListeners() {

        // Main role category cards
        binding.cardBatsman.setOnClickListener {
            selectRoleCategory("batsman")
        }

        binding.cardBowler.setOnClickListener {
            selectRoleCategory("bowler")
        }

        binding.cardBowlingTypes.setOnClickListener {
            selectRoleCategory("bowling_types")
        }

        binding.cardAllRounder.setOnClickListener {
            selectRoleCategory("all_rounder")
        }

        // Bowling style items
        binding.styleRightArmFast.setOnClickListener {
            showBowlingStyleInfo("Right Arm Fast", "Natural angle creates challenges for left-handed batsmen. Most common fast bowling style.")
        }

        binding.styleLeftArmFast.setOnClickListener {
            showBowlingStyleInfo("Left Arm Fast", "Provides variation with natural angle to right-handed batsmen. Creates different bowling angles.")
        }

        binding.styleOffSpinner.setOnClickListener {
            showBowlingStyleInfo("Off Spinner", "Finger spin from off to leg for right-handed batsmen. Uses flight and turn to deceive.")
        }

        binding.styleLegSpinner.setOnClickListener {
            showBowlingStyleInfo("Leg Spinner", "Wrist spin with googlies and variations. Most attacking form of spin bowling.")
        }

        binding.styleLeftArmOrthodox.setOnClickListener {
            showBowlingStyleInfo("Left Arm Orthodox", "Left-arm finger spinner with natural variation. Spins away from right-handed batsmen.")
        }

        binding.styleChinaman.setOnClickListener {
            showBowlingStyleInfo("Chinaman", "Rare left-arm wrist spinner with unique action. One of cricket's most unusual bowling styles.")
        }
    }
    
    private fun selectRoleCategory(roleType: String) {
        if (currentActiveRole == roleType) return // Already selected
        
        currentActiveRole = roleType
        updateCardAppearance(roleType)
        showRoleSection(roleType)
    }
    
    private fun updateCardAppearance(selectedRole: String) {
        // Update Batsman Card
        val isBatsmanSelected = selectedRole == "batsman"
        binding.cardBatsman.setBackgroundResource(
            if (isBatsmanSelected) R.drawable.role_card_dark_background 
            else R.drawable.role_card_background
        )
        binding.textBatsmanTitle.setTextColor(
            ContextCompat.getColor(requireContext(), 
                if (isBatsmanSelected) R.color.text_white else R.color.text_primary
            )
        )
        binding.textBatsmanSubtitle.setTextColor(
            if (isBatsmanSelected) 0xCCFFFFFF.toInt() else ContextCompat.getColor(requireContext(), R.color.text_secondary)
        )
        
        // Update Bowler Card
        val isBowlerSelected = selectedRole == "bowler"
        binding.cardBowler.setBackgroundResource(
            if (isBowlerSelected) R.drawable.role_card_dark_background 
            else R.drawable.role_card_background
        )
        binding.textBowlerTitle.setTextColor(
            ContextCompat.getColor(requireContext(), 
                if (isBowlerSelected) R.color.text_white else R.color.text_primary
            )
        )
        binding.textBowlerSubtitle.setTextColor(
            if (isBowlerSelected) 0xCCFFFFFF.toInt() else ContextCompat.getColor(requireContext(), R.color.text_secondary)
        )
        
        // Update Bowling Types Card
        val isBowlingTypesSelected = selectedRole == "bowling_types"
        binding.cardBowlingTypes.setBackgroundResource(
            if (isBowlingTypesSelected) R.drawable.role_card_dark_background 
            else R.drawable.role_card_background
        )
        binding.textBowlingTypesTitle.setTextColor(
            ContextCompat.getColor(requireContext(), 
                if (isBowlingTypesSelected) R.color.text_white else R.color.text_primary
            )
        )
        binding.textBowlingTypesSubtitle.setTextColor(
            if (isBowlingTypesSelected) 0xCCFFFFFF.toInt() else ContextCompat.getColor(requireContext(), R.color.text_secondary)
        )
        
        // Update All-Rounder Card
        val isAllRounderSelected = selectedRole == "all_rounder"
        binding.cardAllRounder.setBackgroundResource(
            if (isAllRounderSelected) R.drawable.role_card_dark_background 
            else R.drawable.role_card_background
        )
        binding.textAllRounderTitle.setTextColor(
            ContextCompat.getColor(requireContext(), 
                if (isAllRounderSelected) R.color.text_white else R.color.text_primary
            )
        )
        binding.textAllRounderSubtitle.setTextColor(
            if (isAllRounderSelected) 0xCCFFFFFF.toInt() else ContextCompat.getColor(requireContext(), R.color.text_secondary)
        )
    }
    
    private fun showRoleSection(roleType: String) {
        // Hide all sections
        binding.sectionBattingPositions.visibility = View.GONE
        binding.sectionBowlingSpecialists.visibility = View.GONE
        binding.sectionBowlingStyles.visibility = View.GONE
        binding.sectionAllRounder.visibility = View.GONE
        
        // Show the selected section
        when (roleType) {
            "batsman" -> binding.sectionBattingPositions.visibility = View.VISIBLE
            "bowler" -> binding.sectionBowlingSpecialists.visibility = View.VISIBLE
            "bowling_types" -> binding.sectionBowlingStyles.visibility = View.VISIBLE
            "all_rounder" -> binding.sectionAllRounder.visibility = View.VISIBLE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showBowlingStyleInfo(title: String, description: String) {
        val message = "$title: $description"
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
