package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class OdiRankingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_odi_rankings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Optional: tab navigation if these tabs exist on ODI screen in future
        view.findViewById<TextView>(R.id.tabT20i)?.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(T20IRankingsFragment(), "T20I Team Rankings")
        }
        view.findViewById<TextView>(R.id.tabTest)?.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(TestRankingsFragment(), "Test Team Rankings")
        }
    }
}
