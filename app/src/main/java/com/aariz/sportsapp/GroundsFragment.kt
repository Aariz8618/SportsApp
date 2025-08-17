package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GroundsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var groundAdapter: GroundAdapter

    private val groundList = listOf(
        CricketGround("Sydney Cricket Ground", "Sydney, AUS", R.drawable.sydney),
        CricketGround("Melbourne Cricket Ground", "Melbourne, AUS", R.drawable.melbourne),
        CricketGround("Lord's", "London, ENG", R.drawable.lords),
        CricketGround("Kennington Oval", "London, ENG", R.drawable.kennington),
        CricketGround("Adelaide Oval", "Adelaide, AUS", R.drawable.adelaide),
        CricketGround("Wankhede Stadium", "Mumbai, IND", R.drawable.wankhede),
        CricketGround("Eden Gardens", "Kolkata, IND", R.drawable.eden),
        CricketGround("Chinnaswamy Stadium", "Bangalore, IND", R.drawable.chinnaswamy),
        CricketGround("Narendra Modi Stadium", "Ahmedabad, IND", R.drawable.narendra),
        CricketGround("Himachal Pradesh Cricket Association Stadium", "Himachal Pradesh, IND", R.drawable.dharamshala),
        CricketGround("Headingley ", "Leeds, ENG", R.drawable.headingley),
        CricketGround("Old Trafford ", "Manchester, ENG", R.drawable.old_trafford),
        CricketGround("Edgbaston ", "Birmingham, ENG", R.drawable.edgbaston),
        CricketGround("Perth Stadium ", "Perth, AUS", R.drawable.perth),
        CricketGround("Brisbane Cricket Stadium ", "Brisbane, AUS", R.drawable.brisbane),
        CricketGround("The Wanderers Stadium ", "Johannesburg, SA", R.drawable.wanderers),
        CricketGround("SuperSport Park ", "Centurion, SA", R.drawable.supersport),
        CricketGround("Kingsmead ", "Durban, SA", R.drawable.kingsmead),
        CricketGround("Newlands ", "Cape Town, SA", R.drawable.newlands),
        CricketGround("Boland Park ", "Paarl, SA", R.drawable.boland),
        CricketGround("Hagley Oval ", "Christchurch, NZ", R.drawable.haghley),
        CricketGround("Eden Park ", "Auckland, NZ", R.drawable.edenpark),
        CricketGround("Basin Reserve ", "Wellington, NZ", R.drawable.basin),
        CricketGround("Bay Oval ", "Mount Maunganui, NZ", R.drawable.bay),
        CricketGround("Basin Reserve ", "Wellington, NZ", R.drawable.basin),
        CricketGround("Seddon Park ", "Hamilton, NZ", R.drawable.seddon)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grounds, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewGrounds)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        groundAdapter = GroundAdapter(groundList)
        recyclerView.adapter = groundAdapter

        return view
    }
}
