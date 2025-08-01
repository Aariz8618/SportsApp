package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class NzOdiFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.nzodi, container, false)
        
        // Set up click listeners
        setupClickListeners(view)
        
        return view
    }
    
    private fun setupClickListeners(view: View) {
        // Click listener for nz_odi_view1 -> Navigate to NzWc15Fragment
        view.findViewById<TextView>(R.id.nz_odi_view1)?.setOnClickListener {
            Log.d("NzOdiFragment", "Navigating to NzWc15Fragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("NzOdiFragment", "Calling navigateToFragment with NzWc15Fragment")
                    mainActivity.navigateToFragment(NzWc15Fragment(), "New Zealand World Cup 2015")
                } else {
                    Log.e("NzOdiFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("NzOdiFragment", "Error navigating to NzWc15Fragment", e)
                e.printStackTrace()
            }
        }
        
        // Click listener for nz_odi_view3 -> Navigate to NzIndOdiFragment
        view.findViewById<TextView>(R.id.nz_odi_view3)?.setOnClickListener {
            Log.d("NzOdiFragment", "Navigating to NzIndOdiFragment")
            try {
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    Log.d("NzOdiFragment", "Calling navigateToFragment with NzIndOdiFragment")
                    mainActivity.navigateToFragment(NzIndOdiFragment(), "New Zealand vs India ODI")
                } else {
                    Log.e("NzOdiFragment", "MainActivity is null or fragment not properly attached")
                }
            } catch (e: Exception) {
                Log.e("NzOdiFragment", "Error navigating to NzIndOdiFragment", e)
                e.printStackTrace()
            }
        }
    }
}
