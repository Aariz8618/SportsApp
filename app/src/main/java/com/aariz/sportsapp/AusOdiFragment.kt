package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class AusOdiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("AusOdiFragment", "onCreateView called - loading ausodi.xml")
        return try {
            val view = inflater.inflate(R.layout.ausodi, container, false)
            Log.d("AusOdiFragment", "ausodi.xml layout inflated successfully")
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
            title = "Australia ODI Matches"
        }
    }

    private fun setUpClickListeners(view: View) {
        val odiView1 = view.findViewById<TextView>(R.id.aus_odi_view1)
        odiView1?.setOnClickListener {
            try {
                Log.d("AusOdiFragment", "Clicked - ICC World Cup 2023")
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(Iccwc2023Fragment(), "ICC World Cup 2023")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val odiView3 = view.findViewById<TextView>(R.id.aus_odi_view3)
        odiView3?.setOnClickListener {
            try {
                Log.d("AusOdiFragment", "Clicked - Chappell-Hadlee Trophy 2020")
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(Chappell2020Fragment(), "Chappell-Hadlee Trophy 2020")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
