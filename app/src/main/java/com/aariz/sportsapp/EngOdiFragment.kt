package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class EngOdiFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.engodi, container, false)
        
        // Find the TextView elements by their IDs
        val engOdiView1: TextView = view.findViewById(R.id.eng_odi_view1)
        val engOdiView2: TextView = view.findViewById(R.id.eng_odi_view2)
        
        // Set click listener for eng_odi_view1 to navigate to engodiwc.xml
        engOdiView1.setOnClickListener {
            val mainActivity = activity as? MainActivity
            if (mainActivity != null) {
                mainActivity.navigateToFragment(EngOdiWcFragment(), "England - ODI World Cup")
            }
        }
        
        // Set click listener for eng_odi_view2 to navigate to engausodi.xml
        engOdiView2.setOnClickListener {
            val mainActivity = activity as? MainActivity
            if (mainActivity != null) {
                mainActivity.navigateToFragment(EngAusOdiFragment(), "England vs Australia ")
            }
        }
        
        return view
    }
}
