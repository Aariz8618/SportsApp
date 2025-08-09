package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aariz.sportsapp.models.Commentator

class CommentatorsFragment : Fragment() {

    private lateinit var commentatorRecyclerView: RecyclerView
    private lateinit var commentatorAdapter: CommentatorAdapter
    private lateinit var commentatorList: List<Commentator>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_commentators, container, false)

        setupRecyclerView(view)

        return view
    }

    private fun setupRecyclerView(view: View) {
        commentatorRecyclerView = view.findViewById(R.id.commentatorRecyclerView)

        // Grid layout with 2 columns like the image
        commentatorRecyclerView.layoutManager = GridLayoutManager(context, 2)

        // Initialize commentator data
        commentatorList = getCommentatorList()

        // Set adapter
        commentatorAdapter = CommentatorAdapter(commentatorList)
        commentatorRecyclerView.adapter = commentatorAdapter
    }

    private fun getCommentatorList(): List<Commentator> {
        return listOf(
            Commentator("Harsha Bhogle", "India", R.drawable.harsha),
            Commentator("Michael Holding", "West Indies", R.drawable.michael_holding),
            Commentator("Nasser Hussain", "England", R.drawable.nasser_hussain),
            Commentator("Ian Bishop", "West Indies", R.drawable.ian_bishop),
            Commentator("Ravi Shastri", "India", R.drawable.ravi_shastri),
            Commentator("Mark Nicholas", "England", R.drawable.mark_nicholas),
            Commentator("Danny Morrison", "New Zealand", R.drawable.danny_morrison),
            Commentator("Simon Doull", "New Zealand", R.drawable.simon_doull)
        )
    }
}