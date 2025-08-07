package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class NzTestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.nztest, container, false)
        
        // Set up click listeners
        setupClickListeners(view)
        
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Enable back button in action bar
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "New Zealand Test Matches"
        }
    }
    
    private fun setupClickListeners(view: View) {
        // Click listener for nz_test_view1 → Navigate to Wtc21Fragment
        view.findViewById<TextView>(R.id.nz_test_view1)?.setOnClickListener {
            Log.d("NzTestFragment", "Navigating to Wtc21Fragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("NzTestFragment", "Calling navigateToFragment with Wtc21Fragment")
                    mainActivity.navigateToFragment(Wtc21Fragment(), "World Test Championship 2021")
                } else {
                    Log.e("NzTestFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("NzTestFragment", "Error navigating to Wtc21Fragment", e)
                e.printStackTrace()
            }
        }
        
        // Click listener for nz_test_view3 → Navigate to WhitewashFragment
        view.findViewById<TextView>(R.id.nz_test_view3)?.setOnClickListener {
            Log.d("NzTestFragment", "Navigating to WhitewashFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("NzTestFragment", "Calling navigateToFragment with WhitewashFragment")
                    mainActivity.navigateToFragment(WhitewashFragment(), "Whitewash Series")
                } else {
                    Log.e("NzTestFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("NzTestFragment", "Error navigating to WhitewashFragment", e)
                e.printStackTrace()
            }
        }
    }
}
