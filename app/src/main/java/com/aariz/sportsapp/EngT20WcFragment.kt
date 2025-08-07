package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class EngT20WcFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("EngT20WcFragment", "onCreateView called - loading engt20wc.xml")
        return try {
            val view = inflater.inflate(R.layout.engt20wc, container, false)
            Log.d("EngT20WcFragment", "engt20wc.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.engt20wc_featured_image,
            R.id.engt20wc_image_1,
            R.id.engt20wc_image_2,
            R.id.engt20wc_image_3,
            R.id.engt20wc_image_4,
            R.id.engt20wc_image_5,
            R.id.engt20wc_image_6
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
            R.drawable.engt20wc4,
            R.drawable.engt20wc1,
            R.drawable.engt20wc2,
            R.drawable.engt20wc3,
            R.drawable.engt20wc5,
            R.drawable.engt20wc6,
            R.drawable.engt20wc7
        )

        val imageViewIds = listOf(
            R.id.engt20wc_featured_image,
            R.id.engt20wc_image_1,
            R.id.engt20wc_image_2,
            R.id.engt20wc_image_3,
            R.id.engt20wc_image_4,
            R.id.engt20wc_image_5,
            R.id.engt20wc_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
