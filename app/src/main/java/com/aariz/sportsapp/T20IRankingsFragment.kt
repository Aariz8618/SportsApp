package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class T20IRankingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_t20i_rankings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Optional: if segmented tabs exist on this screen in future
        view.findViewById<TextView>(R.id.tabOdi)?.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(OdiRankingsFragment(), "ODI Team Rankings")
        }
        view.findViewById<TextView>(R.id.tabTest)?.setOnClickListener {
            (activity as? MainActivity)?.navigateToFragment(TestRankingsFragment(), "Test Team Rankings")
        }
    }
}
