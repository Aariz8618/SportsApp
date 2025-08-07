package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class AusT20Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("AusT20Fragment", "onCreateView called - loading aust20.xml")
        return try {
            val view = inflater.inflate(R.layout.aust20, container, false)
            Log.d("AusT20Fragment", "aust20.xml layout inflated successfully")
            setUpClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enable back button and set title
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Australia T20I Matches"
        }
    }

    private fun setUpClickListeners(view: View) {
        val t20View1 = view.findViewById<TextView>(R.id.aus_t20_view1)
        t20View1?.setOnClickListener {
            try {
                Log.d("AusT20Fragment", "Clicked - T20 World Cup 2021")
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(T20wc2021Fragment(), "T20 World Cup 2021")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val t20View2 = view.findViewById<TextView>(R.id.aus_t20_view2)
        t20View2?.setOnClickListener {
            try {
                Log.d("AusT20Fragment", "Clicked - Australia vs England - T20 Series")
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(AusEngT20Fragment(), "Australia vs England - T20 Series")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
