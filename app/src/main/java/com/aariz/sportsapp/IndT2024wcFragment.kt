package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class IndT2024wcFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IndT2024wcFragment", "onCreateView called - loading indt2024wc.xml")
        return try {
            val view = inflater.inflate(R.layout.indt2024wc, container, false)
            Log.d("IndT2024wcFragment", "indt2024wc.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.winwc_featured_image, R.id.winwc_image_1, R.id.winwc_image_2,
            R.id.winwc_image_3, R.id.winwc_image_4, R.id.winwc_image_5,
            R.id.winwc_image_6, R.id.winwc_image_7, R.id.winwc_image_8,
            R.id.winwc_image_9, R.id.winwc_image_10, R.id.winwc_image_11,
            R.id.winwc_image_12, R.id.winwc_image_13, R.id.winwc_image_14
        )

        for (id in imageViewIds) {
            val imageView = view.findViewById<ImageView>(id)
            imageView.setOnClickListener {
                showFullScreenImage(it as ImageView, id)
            }
        }
    }

    private fun showFullScreenImage(imageView: ImageView, clickedImageId: Int) {
        val images = listOf(
            R.drawable.it20wc1,  R.drawable.it20wc3,  R.drawable.it20wc4,
            R.drawable.it20wc5,  R.drawable.it20wc6,  R.drawable.it20wc8,
            R.drawable.it20wc9,  R.drawable.it20wc10,  R.drawable.it20wc11,
            R.drawable.it20wc12, R.drawable.it20wc2, R.drawable.it20wc7,
            R.drawable.it20wc14, R.drawable.it20wc15, R.drawable.it20wc16
        )

        val imageViewIds = listOf(
            R.id.winwc_featured_image, R.id.winwc_image_1, R.id.winwc_image_2,
            R.id.winwc_image_3, R.id.winwc_image_4, R.id.winwc_image_5,
            R.id.winwc_image_6, R.id.winwc_image_7, R.id.winwc_image_8,
            R.id.winwc_image_9, R.id.winwc_image_10, R.id.winwc_image_11,
            R.id.winwc_image_12, R.id.winwc_image_13, R.id.winwc_image_14
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
