package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.adapters.ExpertAdapter
import com.aariz.sportsapp.models.Expert

class ExpertsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var expertAdapter: ExpertAdapter

    private val experts = listOf(
        Expert("Sachin Tendulkar", "Batting Legend", R.drawable.sachin),
        Expert("Shane Warne", "Spin Bowling", R.drawable.shane),
        Expert("Ricky Ponting", "Batting & Captaincy", R.drawable.ricky),
        Expert("Brian Lara", "Batting Legend", R.drawable.lara),
        Expert("Wasim Akram", "Swing Bowling", R.drawable.wasim),
        Expert("Jacques Kallis", "All-rounder", R.drawable.kallis),
        Expert("Adam Gilchrist", "Wicketkeeping & Batting", R.drawable.adam),
        Expert("Muttiah Muralitharan", "Spin Bowling", R.drawable.muralitharan)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_experts, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewExperts)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        expertAdapter = ExpertAdapter(experts) { selectedExpert ->
            openExpertDetail(selectedExpert)
        }
        recyclerView.adapter = expertAdapter

        return view
    }

    private fun openExpertDetail(expert: Expert) {
        val fragment = ExpertDetailFragment.newInstance(expert)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // your container id in MainActivity
            .addToBackStack(null)
            .commit()
    }
}
