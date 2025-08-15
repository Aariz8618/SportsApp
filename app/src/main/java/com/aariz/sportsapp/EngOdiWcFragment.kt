package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class EngOdiWcFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("EngOdiWcFragment", "onCreateView called - loading engodiwc.xml")
        return try {
            val view = inflater.inflate(R.layout.engodiwc, container, false)
            Log.d("EngOdiWcFragment", "engodiwc.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.eng_featured_image,
            R.id.eng_image_1,
            R.id.eng_image_2,
            R.id.eng_image_3,
            R.id.eng_image_4,
            R.id.eng_image_5,
            R.id.eng_image_6
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
            R.drawable.engodiwc1,
            R.drawable.engodiwc2,
            R.drawable.engodiwc3,
            R.drawable.engodiwc4,
            R.drawable.engodiwc5,
            R.drawable.engodiwc6,
            R.drawable.engodiwc7
        )

        val imageViewIds = listOf(
            R.id.eng_featured_image,
            R.id.eng_image_1,
            R.id.eng_image_2,
            R.id.eng_image_3,
            R.id.eng_image_4,
            R.id.eng_image_5,
            R.id.eng_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
