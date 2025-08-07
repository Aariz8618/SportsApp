package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class IndTestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IndTestFragment", "onCreateView called - loading indtest.xml")
        return try {
            val view = inflater.inflate(R.layout.indtest, container, false)
            Log.d("IndTestFragment", "indtest.xml layout inflated successfully")
            
            // Set up click listener for BGT 2020/21 View All button
            val bgtViewAll = view.findViewById<TextView>(R.id.bgtview)
            bgtViewAll?.setOnClickListener {
                try {
                    // Navigate to IndtBgt20Fragment via MainActivity
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(IndtBgt20Fragment(), "Border Gavaskar Trophy 2020/21")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            
            // Set up click listener for Anderson - Tendulkar Trophy View All button
            val engView = view.findViewById<TextView>(R.id.engview)
            engView?.setOnClickListener {
                try {
                    // Navigate to IndtAtFragment via MainActivity
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(IndtAtFragment(), "Anderson - Tendulkar Trophy")
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
