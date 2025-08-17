package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class IndtBgt20Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IndtBgt20Fragment", "onCreateView called - loading indtbgt20.xml")
        return try {
            val view = inflater.inflate(R.layout.indtbgt20, container, false)
            Log.d("IndtBgt20Fragment", "indtbgt20.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.bgt_featured_image,
            R.id.bgt_image_1,
            R.id.bgt_image_2,
            R.id.bgt_image_3,
            R.id.bgt_image_4,
            R.id.bgt_image_5,
            R.id.bgt_image_6,
            R.id.bgt_image_7,
            R.id.bgt_image_8,
            R.id.bgt_image_9,
            R.id.bgt_image_10,
            R.id.bgt_image_11,
            R.id.bgt_image_12,
            R.id.bgt_image_13,
            R.id.bgt_image_14
        )

        for (id in imageViewIds) {
            val imageView = view.findViewById<ImageView>(id)
            imageView?.setOnClickListener {
                showFullScreenImage(id)
            }
        }
    }

    private fun showFullScreenImage(clickedImageId: Int) {
        val images = listOf(
            R.drawable.bgt3,
            R.drawable.bgt2,
            R.drawable.bgt1,
            R.drawable.bgt4,
            R.drawable.bgt5,
            R.drawable.bgt6,
            R.drawable.bgt7,
            R.drawable.bgt8,
            R.drawable.bgt9,
            R.drawable.bgt10,
            R.drawable.bgt11,
            R.drawable.bgt12,
            R.drawable.bgt13,
            R.drawable.bgt14,
            R.drawable.bgt15
        )

        val imageViewIds = listOf(
            R.id.bgt_featured_image,
            R.id.bgt_image_1,
            R.id.bgt_image_2,
            R.id.bgt_image_3,
            R.id.bgt_image_4,
            R.id.bgt_image_5,
            R.id.bgt_image_6,
            R.id.bgt_image_7,
            R.id.bgt_image_8,
            R.id.bgt_image_9,
            R.id.bgt_image_10,
            R.id.bgt_image_11,
            R.id.bgt_image_12,
            R.id.bgt_image_13,
            R.id.bgt_image_14
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
