package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentHighlightsBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.aariz.sportsapp.adapters.HighlightPagerAdapter
import com.aariz.sportsapp.adapters.HorizontalCardAdapter
import com.aariz.sportsapp.model.HighlightShow
import com.aariz.sportsapp.model.SimpleShow
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.aariz.sportsapp.ui.SpacingItemDecoration
import android.util.Log

class HighlightsFragment : Fragment() {

    private var _binding: FragmentHighlightsBinding? = null
    private val binding get() = _binding!!
    private val mainHandler = Handler(Looper.getMainLooper())
    private var autoSlideRunnable: Runnable? = null
    private var currentSlide = 0
    private lateinit var highlights: List<HighlightShow>

    companion object {
        private const val TAG = "HighlightsFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHighlightsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            setupData()
            setupCarousel()
            setupLists()
        } catch (e: Exception) {
            Log.e(TAG, "Error in onViewCreated: ${e.message}", e)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoSlide()
        _binding = null
    }

    private fun setupData() {
        highlights = listOf(
            HighlightShow(
                title = "Don't Touch My DAUGHTER",
                subtitle = "Don't Touch My Daughters",
                category = "Drama Unlimited",
                imageUrl = "https://images.unsplash.com/photo-1485846234645-a62644f84728?w=400&h=600&fit=crop"
            ),
            HighlightShow(
                title = "Secret Romance",
                subtitle = "When Love Meets Mystery",
                category = "Romance Special",
                imageUrl = "https://images.unsplash.com/photo-1522075469751-3a6694fb2f61?w=400&h=600&fit=crop"
            ),
            HighlightShow(
                title = "The Crown Prince",
                subtitle = "Royal Hearts Collide",
                category = "Historical Drama",
                imageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=600&fit=crop"
            ),
            HighlightShow(
                title = "Memory Lane",
                subtitle = "Lost in Time",
                category = "Fantasy Romance",
                imageUrl = "https://images.unsplash.com/photo-1519058082700-08a0b56da9b4?w=400&h=600&fit=crop"
            ),
            HighlightShow(
                title = "Midnight Cafe",
                subtitle = "Stories After Dark",
                category = "Slice of Life",
                imageUrl = "https://images.unsplash.com/photo-1521017432531-fbd92d768814?w=400&h=600&fit=crop"
            )
        )
    }

    private fun setupCarousel() {
        try {
            val pagerAdapter = HighlightPagerAdapter(highlights) { highlightShow ->
                // Handle highlight click
                Log.d(TAG, "Clicked on highlight: ${highlightShow.title}")
                // Add your navigation logic here
            }

            binding.viewPagerHighlights.adapter = pagerAdapter
            binding.viewPagerHighlights.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentSlide = position
                    updateIndicators(position)
                    updateCategoryText(position)
                }
            })

            createIndicators(highlights.size)
            updateIndicators(0)
            updateCategoryText(0)
            startAutoSlide()
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up carousel: ${e.message}", e)
        }
    }

    private fun createIndicators(count: Int) {
        try {
            val container: LinearLayout = binding.indicatorContainer
            container.removeAllViews()
            repeat(count) {
                val dot = ImageView(requireContext())
                dot.setImageResource(android.R.drawable.presence_invisible)
                val params = LinearLayout.LayoutParams(16, 16)
                params.marginEnd = 8
                dot.layoutParams = params
                container.addView(dot)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating indicators: ${e.message}", e)
        }
    }

    private fun updateIndicators(active: Int) {
        try {
            val container: LinearLayout = binding.indicatorContainer
            for (i in 0 until container.childCount) {
                val dot = container.getChildAt(i) as? ImageView
                dot?.setImageResource(
                    if (i == active) android.R.drawable.presence_online
                    else android.R.drawable.presence_invisible
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating indicators: ${e.message}", e)
        }
    }

    private fun updateCategoryText(position: Int) {
        try {
            val txt: TextView = binding.txtCategory
            txt.text = highlights.getOrNull(position)?.category ?: ""
        } catch (e: Exception) {
            Log.e(TAG, "Error updating category text: ${e.message}", e)
        }
    }

    private fun startAutoSlide() {
        stopAutoSlide()
        autoSlideRunnable = object : Runnable {
            override fun run() {
                try {
                    val itemCount = binding.viewPagerHighlights.adapter?.itemCount ?: 0
                    if (itemCount > 0) {
                        val next = (currentSlide + 1) % itemCount
                        binding.viewPagerHighlights.setCurrentItem(next, true)
                        mainHandler.postDelayed(this, 5000)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error in auto slide: ${e.message}", e)
                }
            }
        }
        mainHandler.postDelayed(autoSlideRunnable!!, 5000)
    }

    private fun stopAutoSlide() {
        autoSlideRunnable?.let { mainHandler.removeCallbacks(it) }
        autoSlideRunnable = null
    }

    private fun setupLists() {
        try {
            val space = resources.displayMetrics.density.times(8).toInt()

            // Other Shows
            val other = listOf(
                SimpleShow("Marry My Husband", "https://images.unsplash.com/photo-1581833971358-2c8b550f87b3?w=200&h=300&fit=crop", "Romance"),
                SimpleShow("FOREVER LOVE", "https://images.unsplash.com/photo-1583394838336-acd977736f90?w=200&h=300&fit=crop", "Drama")
            )
            setupRecyclerView(binding.rvOtherShows, other, space)

            // WorldCup Finals
            val trending = listOf(
                SimpleShow("THRILLING", "https://images.unsplash.com/photo-1489599735188-c2aa296c4a52?w=300&h=400&fit=crop", "THRILLER"),
                SimpleShow("SCI-FI", "https://images.unsplash.com/photo-1518709268805-4e9042af2176?w=300&h=400&fit=crop", "SCI-FI")
            )
            setupRecyclerView(binding.rvTrendingShows, trending, space)

            // IPL Finals
            val best = listOf(
                SimpleShow("CHAM", "https://images.unsplash.com/photo-1440404653325-ab127d49abc1?w=200&h=300&fit=crop", "DRAMA"),
                SimpleShow("V", "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=200&h=300&fit=crop", "SCI-FI")
            )
            setupRecyclerView(binding.rvBestChoices, best, space)

            //Key Moments
            val billionaire = listOf(
                SimpleShow("CEO's Secret", "https://www.google.com/imgres?q=ipl%20final%20images&imgurl=https%3A%2F%2Fwww.aljazeera.com%2Fwp-content%2Fuploads%2F2024%2F05%2FAP24147688418889-1716750725.jpg%3Fresize%3D1800%252C1800&imgrefurl=https%3A%2F%2Fwww.aljazeera.com%2Fsports%2Fliveblog%2F2024%2F5%2F26%2Flive-kolkata-knight-riders-vs-sunrisers-hyderabad-ipl-2024-final&docid=81z7eO1xNOTQsM&tbnid=7IsPtTo5wjYaMM&vet=12ahUKEwja2rqCrKCPAxW9UGcHHTpkB1UQM3oECCIQAA..i&w=1800&h=1800&hcb=2&ved=2ahUKEwja2rqCrKCPAxW9UGcHHTpkB1UQM3oECCIQAA"),
                SimpleShow("Rich Love", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150&h=200&fit=crop"),
                SimpleShow("Diamond Heart", "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&h=200&fit=crop")
            )
            setupRecyclerView(binding.rvBillionaire, billionaire, space)

        } catch (e: Exception) {
            Log.e(TAG, "Error setting up lists: ${e.message}", e)
        }
    }

    private fun setupRecyclerView(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        items: List<SimpleShow>,
        spacing: Int
    ) {
        try {
            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            recyclerView.adapter = HorizontalCardAdapter(items) { show ->
                // Handle item click
                Log.d(TAG, "Clicked on show: ${show.title}")
                // Add your navigation logic here
            }
            recyclerView.addItemDecoration(SpacingItemDecoration(spacing))
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up RecyclerView: ${e.message}", e)
        }
    }
}