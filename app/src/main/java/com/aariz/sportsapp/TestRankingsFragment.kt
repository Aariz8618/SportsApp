package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class TestRankingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_rankings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up tab clicks to navigate to ODI and T20I rankings
        view.findViewById<TextView>(R.id.tabOdi)?.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(OdiRankingsFragment(), "ODI Team Rankings")
        }
        view.findViewById<TextView>(R.id.tabT20i)?.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(T20IRankingsFragment(), "T20I Team Rankings")
        }
    }
}
