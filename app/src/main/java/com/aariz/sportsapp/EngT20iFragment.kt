package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class EngT20iFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("EngT20iFragment", "onCreateView called - loading engt20i.xml")
        return try {
            val view = inflater.inflate(R.layout.engt20i, container, false)
            Log.d("EngT20iFragment", "engt20i.xml layout inflated successfully")

            // Set up click listener for England vs New Zealand T20
            val t20ViewNz = view.findViewById<TextView>(R.id.eng_t20_view3)
            t20ViewNz?.setOnClickListener {
                try {
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(EngNzT20Fragment(), "England vs New Zealand - T20")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            // Set up click listener for T20 World Cup 2021
            val t20ViewWc = view.findViewById<TextView>(R.id.eng_t20_view1)
            t20ViewWc?.setOnClickListener {
                try {
                    val mainActivity = activity as? MainActivity
                    if (mainActivity != null && isAdded && !isDetached) {
                        mainActivity.navigateToFragment(EngT20WcFragment(), "T20 World Cup 2021")
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
