package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class WiT20Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("WiT20Fragment", "onCreateView called - loading wit20.xml")
        return try {
            val view = inflater.inflate(R.layout.wit20, container, false)
            Log.d("WiT20Fragment", "wit20.xml layout inflated successfully")
            
            // Set up click listener for West Indies T20 World Cup 2024 View All button
            val wiT20View1 = view.findViewById<TextView>(R.id.wi_t20_view1)
            wiT20View1?.setOnClickListener {
                try {
                    // Navigate to Wiwc12Fragment for West Indies T20 World Cup 2012
                    Log.d("WiT20Fragment", "West Indies T20 World Cup 2024 View All clicked")
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(Wiwc12Fragment(), "West Indies T20 World Cup 2012")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            
            // Set up click listener for West Indies T20 World Cup 2022 View All button (coming soon)
            val wiT20View2 = view.findViewById<TextView>(R.id.wi_t20_view2)
            wiT20View2?.setOnClickListener {
                try {
                    // This is a coming soon item, could show a toast or do nothing
                    Log.d("WiT20Fragment", "West Indies T20 World Cup 2022 View All clicked (Coming Soon)")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            
            // Set up click listener for West Indies vs Pakistan 2021 View All button
            val wiT20View3 = view.findViewById<TextView>(R.id.wi_t20_view3)
            wiT20View3?.setOnClickListener {
                try {
                    // Navigate to Wiwc16Fragment for West Indies T20 World Cup 2016
                    Log.d("WiT20Fragment", "West Indies vs Pakistan 2021 View All clicked")
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(Wiwc16Fragment(), "West Indies T20 World Cup 2016")
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
