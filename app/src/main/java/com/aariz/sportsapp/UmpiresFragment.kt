package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UmpiresFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UmpireAdapter
    private lateinit var umpireList: List<Umpire>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_umpire, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewUmpire)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        umpireList = listOf(
            Umpire("Chris Gaffaney", "New Zealand", R.drawable.chris),
            Umpire("Michael Gough", "England", R.drawable.gough),
            Umpire("Richard Illingworth", "England", R.drawable.illingworth),
            Umpire("Richard Kettleborough", "England", R.drawable.kettleborough),
            Umpire("Nitin Menon", "India", R.drawable.nitin),
            Umpire("Ahsan Raza", "Pakistan", R.drawable.ahsan),
            Umpire("Paul Reiffel", "Australia", R.drawable.paul),
            Umpire("Adrian Holdstock", "South Africa", R.drawable.adrian),
            Umpire("Kumar Dharmasena", "Sri Lanka", R.drawable.kumar_dharmasena),
            Umpire("Sharfuddoula", "Bangladesh", R.drawable.sharfuddoula),
            Umpire("Rod Tucker", "Australia", R.drawable.tucker),
            Umpire("Joel Wilson", "West Indies", R.drawable.joel),
            Umpire("David Boon", "West Indies", R.drawable.david),
            Umpire("Ranjan Madugalle", "West Indies", R.drawable.ranjan),
            Umpire("Andy Pycroft", "West Indies", R.drawable.andy),
            Umpire("Richie Richardson", "West Indies", R.drawable.richie),
            Umpire("Javagal Srinath", "West Indies", R.drawable.javagal),
            Umpire("Jeff Crowe", "West Indies", R.drawable.jeff),

            )

        adapter = UmpireAdapter(umpireList)
        recyclerView.adapter = adapter

        return view
    }
}
