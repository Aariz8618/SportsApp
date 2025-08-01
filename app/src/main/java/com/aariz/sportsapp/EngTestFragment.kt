package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class EngTestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.engtest, container, false)
        
        // Find the TextView elements by their IDs
        val engTestView1: TextView = view.findViewById(R.id.eng_test_view1)
        val engTestView3: TextView = view.findViewById(R.id.eng_test_view3)
        
        // Set click listener for eng_test_view1 to navigate to engashes.xml
        engTestView1.setOnClickListener {
            val mainActivity = activity as? MainActivity
            if (mainActivity != null) {
                mainActivity.navigateToFragment(EngAshesFragment(), "England Ashes")
            }
        }
        
        // Set click listener for eng_test_view3 to navigate to engind12.xml
        engTestView3.setOnClickListener {
            val mainActivity = activity as? MainActivity
            if (mainActivity != null) {
                mainActivity.navigateToFragment(EngInd12Fragment(), "England India 12")
            }
        }
        
        return view
    }
}
