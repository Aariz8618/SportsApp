package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment



class IndEngTestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("IndEngTestFragment", "onCreateView called - loading indengtest.xml")
        return try {
            val view = inflater.inflate(R.layout.indengtest, container, false)
            Log.d("IndEngTestFragment", "indengtest.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.inde_featured_image, R.id.inde_image_1, R.id.inde_image_2,
            R.id.inde_image_3, R.id.inde_image_4, R.id.inde_image_5,
            R.id.inde_image_6, R.id.inde_image_7, R.id.inde_image_8,
            R.id.inde_image_9, R.id.inde_image_10
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
            R.drawable.inde1,  R.drawable.inde2,  R.drawable.inde3,
            R.drawable.inde4,  R.drawable.inde5,  R.drawable.inde6,
            R.drawable.inde7,  R.drawable. inde8,  R.drawable.inde9,
            R.drawable.inde10, R.drawable.inde11)

        val imageViewIds = listOf(
            R.id.inde_featured_image, R.id.inde_image_1, R.id.inde_image_2,
            R.id.inde_image_3, R.id.inde_image_4, R.id.inde_image_5,
            R.id.inde_image_6, R.id.inde_image_7, R.id.inde_image_8,
            R.id.inde_image_9, R.id.inde_image_10
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
