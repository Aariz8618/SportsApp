package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class Chappell2020Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Chappell2020Fragment", "onCreateView called - loading chappell2020.xml")
        return try {
            val view = inflater.inflate(R.layout.chappell2020, container, false)
            Log.d("Chappell2020Fragment", "chappell2020.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.chappell2020_featured_image,
            R.id.chappell2020_image_1,
            R.id.chappell2020_image_2,
            R.id.chappell2020_image_3,
            R.id.chappell2020_image_4,
            R.id.chappell2020_image_5,
            R.id.chappell2020_image_6
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
            R.drawable.ausnzo1, // chappell2020_featured_image
            R.drawable.ausnzo9,
            R.drawable.ausnzo8,
            R.drawable.ausnzo6,
            R.drawable.ausnzo7,
            R.drawable.ausnzo5,
            R.drawable.ausnzo4
        )

        val imageViewIds = listOf(
            R.id.chappell2020_featured_image,
            R.id.chappell2020_image_1,
            R.id.chappell2020_image_2,
            R.id.chappell2020_image_3,
            R.id.chappell2020_image_4,
            R.id.chappell2020_image_5,
            R.id.chappell2020_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
