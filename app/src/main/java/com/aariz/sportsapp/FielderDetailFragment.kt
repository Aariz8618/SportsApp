package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentFielderDetailBinding
import com.google.android.material.button.MaterialButtonToggleGroup

class FielderDetailFragment : Fragment() {

    private var _binding: FragmentFielderDetailBinding? = null
    private val binding get() = _binding!!
    private var positionNameArg: String = "Unknown Position"
    private var playerIdArg: String? = null
    private var currentSectionIndex: Int = 0

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
        positionNameArg = arguments?.getString(ARG_POSITION_NAME) ?: "Unknown Position"
        playerIdArg = arguments?.getString(ARG_PLAYER_ID)

        binding.tvPositionName.text = positionNameArg
        binding.tvPositionSubtitle.text = if (!playerIdArg.isNullOrEmpty()) {
            "Mapped Player ID: $playerIdArg"
        } else {
            "No player mapped to this position"
        }

        binding.tvPositionDetails.text =
            "Details about $positionNameArg will appear here. Add description, typical fielding situations, and coaching tips."

        setupTabs()
        setupButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION_NAME = "position_name"
        const val ARG_PLAYER_ID = "player_id"
    }

    private fun setupTabs() {
        val toggle: MaterialButtonToggleGroup = binding.toggleTabs
        // Ensure Overview selected by default
        binding.btnTabOverview.isChecked = true
        showSection(0, animate = false)

        toggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            when (checkedId) {
                binding.btnTabOverview.id -> showSection(0)
                binding.btnTabTactics.id -> showSection(1)
                binding.btnTabStats.id -> showSection(2)
                binding.btnTabFamous.id -> showSection(3)
            }
        }
    }

    private fun showSection(index: Int, animate: Boolean = true) {
        val overview = binding.sectionOverview
        val tactics = binding.sectionTactics
        val stats = binding.sectionStatistics
        val famous = binding.sectionFamous

        val sections = listOf(overview, tactics, stats, famous)

        if (!animate || currentSectionIndex == index) {
            sections.forEachIndexed { i, v -> v.visibility = if (i == index) View.VISIBLE else View.GONE }
            currentSectionIndex = index
            return
        }

        val outView = sections[currentSectionIndex]
        val inView = sections[index]

        if (inView.visibility != View.VISIBLE) {
            inView.alpha = 0f
            inView.visibility = View.VISIBLE
        }

        // Crossfade
        val duration = 180L
        outView.animate().alpha(0f).setDuration(duration).withEndAction {
            outView.visibility = View.GONE
            outView.alpha = 1f
        }.start()

        inView.animate().alpha(1f).setDuration(duration).start()

        currentSectionIndex = index
    }

    private fun setupButtons() {
        // Use the toggle group so selected button turns green (checked) and the other stays white
        binding.actionToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            when (checkedId) {
                binding.btnViewOnMap.id -> {
                    val fragment = FieldingPositionsFragment().apply {
                        arguments = Bundle().apply {
                            putBoolean(FieldingPositionsFragment.ARG_SINGLE_POSITION_MODE, true)
                            putString(FieldingPositionsFragment.ARG_SINGLE_POSITION_NAME, positionNameArg)
                        }
                    }

                    // Use MainActivity's navigation method to properly handle back navigation
                    val handledByActivity = (activity as? MainActivity)?.let { main ->
                        main.navigateToFragment(
                            fragment,
                            title = "Field View: $positionNameArg",
                            addToBackStack = true
                        )
                        true
                    } ?: false

                    if (!handledByActivity) {
                        // Fallback: Add to back stack so we can return to FielderDetailFragment
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                }
                binding.btnComparePositions.id -> {
                    val fragment = FieldingPositionsFragment().apply {
                        arguments = Bundle().apply {
                            putBoolean(FieldingPositionsFragment.ARG_COMPARE_MODE, true)
                            putStringArrayList(
                                FieldingPositionsFragment.ARG_PRESELECTED_POSITIONS,
                                arrayListOf(positionNameArg)
                            )
                        }
                    }

                    // Use MainActivity's navigation method to properly handle back navigation
                    val handledByActivity = (activity as? MainActivity)?.let { main ->
                        main.navigateToFragment(
                            fragment,
                            title = "Compare Positions",
                            addToBackStack = true
                        )
                        true
                    } ?: false

                    if (!handledByActivity) {
                        // Fallback: Add to back stack so we can return to FielderDetailFragment
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }
    }
}
