package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback

class PositionsComparisonFragment : Fragment() {

    private var positions: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        positions = arguments?.getStringArrayList(ARG_POSITIONS) ?: arrayListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_positions_comparison, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()

        // Ensure system back pops this fragment
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (parentFragmentManager.backStackEntryCount > 0) {
                    parentFragmentManager.popBackStack()
                } else {
                    // Delegate to activity's handler
                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        val container = view.findViewById<LinearLayout>(R.id.compare_container)
        container?.removeAllViews()

        // Simple side-by-side cards for each selected position
        positions.forEach { name ->
            container?.addView(createPositionCard(name))
        }
    }

    private fun createPositionCard(name: String): View {
        val context = requireContext()

        val card = CardView(context).apply {
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply {
                val margin = (8 * resources.displayMetrics.density).toInt()
                setMargins(margin, margin, margin, margin)
            }
            radius = 16f
            cardElevation = 6f
            useCompatPadding = true
        }

        val tv = TextView(context).apply {
            text = name
            textSize = 18f
            setPadding((16 * resources.displayMetrics.density).toInt())
        }

        card.addView(tv)
        return card
    }

    private fun setTitle() {
        activity?.title = "Compare Positions"
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Compare Positions"
        // Update custom header in MainActivity if present
        (activity as? MainActivity)?.updateHeaderTitle("Compare Positions")
    }

    companion object {
        const val ARG_POSITIONS = "arg_positions"
    }
}
