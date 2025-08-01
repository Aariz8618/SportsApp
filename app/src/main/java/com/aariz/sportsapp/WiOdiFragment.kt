package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class WiOdiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("WiOdiFragment", "onCreateView called - loading wiodi.xml")
        return try {
            val view = inflater.inflate(R.layout.wiodi, container, false)
            Log.d("WiOdiFragment", "wiodi.xml layout inflated successfully")
            
            // Set up click listener for West Indies ODI World Cup 2023 View All button
            val wiOdiView1 = view.findViewById<TextView>(R.id.wi_odi_view1)
            wiOdiView1?.setOnClickListener {
                try {
                    // Navigate to Winz12Fragment for West Indies vs New Zealand 2012
                    Log.d("WiOdiFragment", "West Indies ODI World Cup 2023 View All clicked")
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(Winz12Fragment(), "West Indies vs New Zealand 2012")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            
            // Set up click listener for West Indies vs sl View All button
            val wiOdiView3 = view.findViewById<TextView>(R.id.wi_odi_view3)
            wiOdiView3?.setOnClickListener {
                try {
                    // Navigate to Wieng07Fragment for West Indies vs England 2007
                    Log.d("WiOdiFragment", "West Indies vs India 2017 View All clicked")
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(Wieng07Fragment(), "West Indies vs Srilanka 2007")
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
