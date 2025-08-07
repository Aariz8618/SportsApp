package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class IndodiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IndodiFragment", "onCreateView called - loading indodi.xml")
        return try {
            val view = inflater.inflate(R.layout.indodi, container, false)
            Log.d("IndodiFragment", "indodi.xml layout inflated successfully")
            
            
            // Set up click listener for World Cup View All button
            val wcView = view.findViewById<TextView>(R.id.wcview)
            wcView?.setOnClickListener {
                try {
                    // Navigate to IndodiwcFragment via MainActivity
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(IndodiwcFragment(), "India ODI World Cup")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            
            // Set up click listener for Series View All button
            val serView = view.findViewById<TextView>(R.id.serview)
            serView?.setOnClickListener {
                try {
                    // Navigate to IndodiausFragment via MainActivity
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(IndodiausFragment(), "India vs Australia ODI Series")
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
