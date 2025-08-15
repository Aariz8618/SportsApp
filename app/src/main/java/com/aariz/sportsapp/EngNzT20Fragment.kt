package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class EngNzT20Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("EngNzT20Fragment", "onCreateView called - loading engnzt20.xml")
        return try {
            val view = inflater.inflate(R.layout.engnzt20, container, false)
            Log.d("EngNzT20Fragment", "engnzt20.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.engnzt20_featured_image,
            R.id.engnzt20_image_1,
            R.id.engnzt20_image_2,
            R.id.engnzt20_image_3,
            R.id.engnzt20_image_4,
            R.id.engnzt20_image_5,
            R.id.engnzt20_image_6
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
            R.drawable.engnzt8,   // engnzt20_featured_image
            R.drawable.engnzt2,
            R.drawable.engnzt1,
            R.drawable.engnzt3,
            R.drawable.engnzt5,
            R.drawable.engnzt6,
            R.drawable.engnzt7
        )

        val imageViewIds = listOf(
            R.id.engnzt20_featured_image,
            R.id.engnzt20_image_1,
            R.id.engnzt20_image_2,
            R.id.engnzt20_image_3,
            R.id.engnzt20_image_4,
            R.id.engnzt20_image_5,
            R.id.engnzt20_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
