package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class NzT20iFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.nzt20i, container, false)
        
        // Set up click listeners
        setupClickListeners(view)
        
        return view
    }
    
    private fun setupClickListeners(view: View) {
        // Click listener for nz_t20_view1 → Navigate to NzTwc21Fragment
        view.findViewById<TextView>(R.id.nz_t20_view1)?.setOnClickListener {
            Log.d("NzT20iFragment", "Navigating to NzTwc21Fragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("NzT20iFragment", "Calling navigateToFragment with NzTwc21Fragment")
                    mainActivity.navigateToFragment(NzTwc21Fragment(), "New Zealand T20 World Cup 2021")
                } else {
                    Log.e("NzT20iFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("NzT20iFragment", "Error navigating to NzTwc21Fragment", e)
                e.printStackTrace()
            }
        }
        
        // Click listener for nz_t20_view3 → Navigate to NzIndT20Fragment
        view.findViewById<TextView>(R.id.nz_t20_view3)?.setOnClickListener {
            Log.d("NzT20iFragment", "Navigating to NzIndT20Fragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("NzT20iFragment", "Calling navigateToFragment with NzIndT20Fragment")
                    mainActivity.navigateToFragment(NzIndT20Fragment(), "New Zealand vs India T20I")
                } else {
                    Log.e("NzT20iFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("NzT20iFragment", "Error navigating to NzIndT20Fragment", e)
                e.printStackTrace()
            }
        }
    }
}
