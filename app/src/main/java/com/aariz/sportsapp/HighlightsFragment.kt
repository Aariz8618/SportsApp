package com.aariz.sportsapp

import android.content.Intent
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
        // Sample public MP4 URLs
        val video1 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val video2 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        val video3 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"
        val video4 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"
        val video5 = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"

        highlights = listOf(
            HighlightShow(
                title = "AUSTRALIA vs SOUTH AFRICA\n1st ODI",
                subtitle = "Highlights",
                imageUrl = "https://static.cricbuzz.com/a/img/v1/i1/c740195/australias-batting-vs-south-africa-1st-odi.jpg?d=low&p=det",
                videoUrl = video1
            ),
            HighlightShow(
                title = "AUSTRALIA vs SOUTH AFRICA\n 2nd ODI",
                subtitle = "Highlights",
                imageUrl = "https://static.cricbuzz.com/a/img/v1/i1/c741490/highlights-australia-vs-south-africa-2nd-odi.jpg?d=low&p=det",
                videoUrl = video2
            ),
            HighlightShow(
                title = "AUSTRALIA vs SOUTH AFRICA\n1st T20I",
                subtitle = "Highlights",
                imageUrl = "https://static.cricbuzz.com/a/img/v1/i1/c736178/south-africas-batting-v-australia-1st-t20i.jpg?d=low&p=det",
                videoUrl = video3
            ),
            HighlightShow(
                title = "AUSTRALIA vs SOUTH AFRICA\n2nd T20I",
                subtitle = "Highlights",
                imageUrl = "https://static.cricbuzz.com/a/img/v1/1080x608/i1/c736886/highlights-australia-vs-south-africa-2nd-t20i.jpg",
                videoUrl = video4
            ),
            HighlightShow(
                title = "AUSTRALIA vs SOUTH AFRICA\n3rd T20I",
                subtitle = "Highlights",
                imageUrl = "https://img1.hotstarext.com/image/upload/f_auto/sources/r1/cms/prod/4639/1755356664639-i",
                videoUrl = video5
            )
        )
    }

    private fun setupCarousel() {
        try {
            val pagerAdapter = HighlightPagerAdapter(highlights) { highlightShow ->
                // Navigate to player screen
                val ctx = requireContext()
                val intent = Intent(ctx, HighlightPlayerActivity::class.java).apply {
                    putExtra(HighlightPlayerActivity.EXTRA_TITLE, highlightShow.title)
                    putExtra(HighlightPlayerActivity.EXTRA_SUBTITLE, highlightShow.subtitle)
                    putExtra(HighlightPlayerActivity.EXTRA_VIDEO_URL, highlightShow.videoUrl)
                }
                startActivity(intent)
            }

            binding.viewPagerHighlights.adapter = pagerAdapter
            binding.viewPagerHighlights.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentSlide = position
                    updateIndicators(position)
                }
            })

            createIndicators(highlights.size)
            updateIndicators(0)
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
                SimpleShow("ABD’s 31-Ball Masterclass", "https://www.zapcricket.com/cdn/shop/articles/WhatsApp_Image_2024-02-01_at_05.12.07.webp?v=1706745388" ),
                SimpleShow("Malinga’s Four-in-Four Magic", "https://img1.hscicdn.com/image/upload/f_auto/lsci/db/PICTURES/CMS/294200/294235.6.jpg")
            )
            setupRecyclerView(binding.rvOtherShows, other, space)

            // WorldCup Finals
            val worldcup = listOf(
                SimpleShow("WORLD CUP 2019 FINAL\n ENG vs NZ ", "https://akm-img-a-in.tosshub.com/indiatoday/images/story/202407/eoin-morgans-england-140215708-16x9_0.jpg?VersionId=9_SpKCvU_e8favxXvHS8lSAy0x7MLLO_&size=690:388"),
                SimpleShow("WORLD CUP 2023 FINAL\n AUS vs IND", "https://e0.365dm.com/23/11/768x432/skysports-cricket-world-cup-final_6365829.jpg?20231119165739"),
                SimpleShow("ASIA CUP FINAL 2023\n IND vs SL", "https://www.hindustantimes.com/ht-img/img/2023/09/17/optimize/CRICKET-ASIA-2023-IND-SRI-ODI-PODIUM-6_1694958650972_1694958654821.jpg")
            )
            setupRecyclerView(binding.rvTrendingShows, worldcup, space)

            // IPL Finals
            val ipl = listOf(
                SimpleShow("IPL FINAL 2012\n KKR vs CSK","https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjvGggJYIWYq8kdt6xYLPL_aLNFziIQRaY6Dyn-m-PIYy-ctNcJ5V5zBh5dIVQA5FTwCYiDz5rbGR9zHE0a4wQyn_Y-xS6u8XR05EpqlcufRe72NE1ERzhUcKNQOWHCPPJMd7eXS_eIEL0/s900/146018.6.jpg"),
                SimpleShow("IPL FINAL 2013\n MI vs CSK", "https://www.hindustantimes.com/ht-img/img/2023/05/04/1600x900/mi-2013_1683198503255_1683198507333.jpg"),
                SimpleShow("IPL FINAL 2018\n CSK vs SRH", "https://documents.iplt20.com/bcci/videos/1746379013_JRV_0142.jpg" )
            )
            setupRecyclerView(binding.rvBestChoices, ipl, space)

            //Key Moments
            val key_moments = listOf(
                SimpleShow("Shami’s Semifinal Storm", "https://i.ytimg.com/vi/5QI90uLry_g/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLD8MAZX85Ycf5YH3NwySB6g4z2yVA"),
                SimpleShow("Big Show’s Greatest Show", "https://images.indianexpress.com/2023/11/maxwell-vs-AFG.jpg"),
                SimpleShow("The Chase Master Strikes Again", "https://img1.hscicdn.com/image/upload/f_auto/lsci/db/PICTURES/CMS/348000/348082.6.jpg")
            )
            setupRecyclerView(binding.rvBillionaire, key_moments, space)

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
                // For now, these items do not have video URLs. You can map them to videos later.
                Log.d(TAG, "Clicked on show: ${show.title}")
            }
            recyclerView.addItemDecoration(SpacingItemDecoration(spacing))
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up RecyclerView: ${e.message}", e)
        }
    }
}