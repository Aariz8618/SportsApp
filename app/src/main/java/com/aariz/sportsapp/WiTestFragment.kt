package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class WiTestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("WiTestFragment", "onCreateView called - loading witest.xml")
        return try {
            val view = inflater.inflate(R.layout.witest, container, false)
            Log.d("WiTestFragment", "witest.xml layout inflated successfully")
            
            // Set up click listener for West Indies Test Series 2024 View All button
            val wiTestView1 = view.findViewById<TextView>(R.id.wi_test_view1)
            wiTestView1?.setOnClickListener {
                try {
                    // Navigate to ShamarausFragment for Shamar vs Australia
                    Log.d("WiTestFragment", "West Indies Test Series 2024 View All clicked")
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(ShamarausFragment(), "Shamar vs Australia")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            
            // Set up click listener for West Indies vs England 2022 View All button
            val wiTestView3 = view.findViewById<TextView>(R.id.wi_test_view3)
            wiTestView3?.setOnClickListener {
                try {
                    // Navigate to Lara400Fragment for Lara 400 runs
                    Log.d("WiTestFragment", "West Indies vs England 2022 View All clicked")
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(Lara400Fragment(), "Lara 400 not out")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
