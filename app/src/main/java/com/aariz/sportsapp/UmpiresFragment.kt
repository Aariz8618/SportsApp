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
            Umpire("Chris Gaffaney", "New Zealand", R.drawable.chris1),
            Umpire("Michael Gough", "England", R.drawable.michael1),
            Umpire("Richard Illingworth", "England", R.drawable.illingworth1),
            Umpire("Richard Kettleborough", "England", R.drawable.kettleborough1),
            Umpire("Nitin Menon", "India", R.drawable.nitin1),
            Umpire("Ahsan Raza", "Pakistan", R.drawable.ahsan1),
            Umpire("Paul Reiffel", "Australia", R.drawable.paul1),
            Umpire("Adrian Holdstock", "South Africa", R.drawable.adrian1),
            Umpire("Kumar Dharmasena", "Sri Lanka", R.drawable.kumar_dharmsena1),
            Umpire("Sharfuddoula", "Bangladesh", R.drawable.sharfuddoula1),
            Umpire("Rod Tucker", "Australia", R.drawable.tucker1),
            Umpire("Joel Wilson", "West Indies", R.drawable.joel1),
            Umpire("Anil Chaudhary", "India", R.drawable.anil),
            Umpire("Simon Taufel", "Australia", R.drawable.simon),
            Umpire("Aleem Dar", "Pakistan", R.drawable.aleem),
            Umpire("Steve Bucknor", "Jamaica", R.drawable.buckner),
            Umpire("Brian Jerling", "South Africa", R.drawable.brian),
            Umpire("Bruce Oxenford", "Australia", R.drawable.bruce)
        )

        adapter = UmpireAdapter(umpireList) { selectedUmpire ->
            openUmpireDetail(selectedUmpire)
        }
        recyclerView.adapter = adapter

        return view
    }

    private fun openUmpireDetail(umpire: Umpire) {
        val fragment = UmpireDetailFragment.newInstance(umpire)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
