package com.aariz.sportsapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aariz.sportsapp.databinding.FragmentMemesBinding

class MemesFragment : Fragment() {

    private var _binding: FragmentMemesBinding? = null
    private val binding get() = _binding!!
    private lateinit var memesAdapter: MemesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val memesList = createMemesList()
        
        memesAdapter = MemesAdapter(memesList) { meme ->
            onMemeClicked(meme, memesList)
        }
        
        binding.memesRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = memesAdapter
            setHasFixedSize(true)
        }
    }

    private fun createMemesList(): List<Meme> {
        return listOf(
            Meme(1, R.drawable.meme4, "Cricket Humor"),
            Meme(2, R.drawable.meme1, "Funny Cricket Moment"),
            Meme(3, R.drawable.meme14, "Match Day Vibes"),
            Meme(4, R.drawable.meme2, "Stadium Fun"),
            Meme(5, R.drawable.meme5, "Cricket Memes"),
            Meme(6, R.drawable.meme6, "Sports Comedy"),
            Meme(7, R.drawable.meme7, "Cricket Life"),
            Meme(8, R.drawable.meme8, "Match Reactions"),
            Meme(9, R.drawable.meme9, "Cricket Logic"),
            Meme(10, R.drawable.meme10, "Game Day"),
            Meme(11, R.drawable.meme11, "Cricket Culture"),
            Meme(12, R.drawable.meme12, "Sports Humor"),
            Meme(13, R.drawable.meme13, "Cricket Fun"),
            Meme(14, R.drawable.meme3, "Match Time")
        )
    }

    private fun onMemeClicked(meme: Meme, memesList: List<Meme>) {
        // Find the position of the clicked meme
        val position = memesList.indexOf(meme)
        
        // Launch full screen meme activity with transition animation
        val intent = Intent(requireContext(), FullScreenMemeActivity::class.java).apply {
            putParcelableArrayListExtra(FullScreenMemeActivity.EXTRA_MEMES_LIST, ArrayList(memesList))
            putExtra(FullScreenMemeActivity.EXTRA_CURRENT_POSITION, position)
        }
        
        // Create smooth transition animation
        val options = ActivityOptionsCompat.makeCustomAnimation(
            requireContext(),
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        
        startActivity(intent, options.toBundle())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
