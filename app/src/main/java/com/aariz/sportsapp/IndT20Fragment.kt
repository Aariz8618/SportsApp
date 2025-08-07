package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class IndT20Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IndT20Fragment", "onCreateView called - loading indt20.xml")
        return try {
            val view = inflater.inflate(R.layout.indt20, container, false)
            Log.d("IndT20Fragment", "indt20.xml layout inflated successfully")
            
            // Set up click listener for T20 World Cup View All button (t20wcview)
            val t20wcView = view.findViewById<TextView>(R.id.t20wcview)
            t20wcView?.setOnClickListener {
                try {
                    Log.d("IndT20Fragment", "T20 World Cup View All (t20wcview) clicked")
                    // Navigate to T20 World Cup 2024 details fragment
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(IndT2024wcFragment(), "ICC T20 World Cup 2024")
                    }
                } catch (e: Exception) {
                    Log.e("IndT20Fragment", "Error in t20wcview click", e)
                    e.printStackTrace()
                }
            }
            
            // Set up click listener for T20 Series/Album View All button (t20bview)
            val t20bView = view.findViewById<TextView>(R.id.t20bview)
            t20bView?.setOnClickListener {
                try {
                    Log.d("IndT20Fragment", "T20 Series View All (t20bview) clicked")
                    // Navigate to India vs New Zealand 2020 fragment
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(IndNz2020Fragment(), "India vs New Zealand 2020")
                    }
                } catch (e: Exception) {
                    Log.e("IndT20Fragment", "Error in t20bview click", e)
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
