package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class AusTestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("AusTestFragment", "onCreateView called - loading austest.xml")
        val view = inflater.inflate(R.layout.austest, container, false)
        setUpViewAllClickListeners(view)
        return view
    }

    private fun setUpViewAllClickListeners(view: View) {
        // Set up click listener for first Test card "View All" button
        val testView1 = view.findViewById<TextView>(R.id.aus_test_view1)
        testView1?.setOnClickListener {
            try {
                Log.d("AusTestFragment", "Australia Test View All 1 clicked - WTC 2023 Australia")
                // Navigate to WTC 2023 Australia gallery fragment
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(Wtc23ausFragment(), "WTC 2023 Australia")
                }
            } catch (e: Exception) {
                Log.e("AusTestFragment", "Error in Test view 1 click", e)
                e.printStackTrace()
            }
        }

        // Set up click listener for second Test card "View All" button
        val testView2 = view.findViewById<TextView>(R.id.aus_test_view2)
        testView2?.setOnClickListener {
            try {
                Log.d("AusTestFragment", "Australia Test View All 2 clicked - The Ashes 2023")
                // Navigate to The Ashes 2023 gallery fragment
                val mainActivity = activity as? MainActivity
                if (mainActivity != null && isAdded && !isDetached) {
                    mainActivity.navigateToFragment(Ashes23Fragment(), "The Ashes 2023")
                }
            } catch (e: Exception) {
                Log.e("AusTestFragment", "Error in Test view 2 click", e)
                e.printStackTrace()
            }
        }
    }
}
