package com.example.sportsapp1.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.R


class IndTestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Make sure the layout file exists: res/layout/indtest.xml
        return inflater.inflate(R.layout.indtest, container, false)
    }
}