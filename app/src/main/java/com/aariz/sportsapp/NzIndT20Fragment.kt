package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class NzIndT20Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("NzIndT20Fragment", "onCreateView called - loading nzindt20.xml")
        return try {
            val view = inflater.inflate(R.layout.nzindt20, container, false)
            Log.d("NzIndT20Fragment", "nzindt20.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.nzindT20_featured_image,
            R.id.nzindT20_image_1,
            R.id.nzindT20_image_2,
            R.id.nzindT20_image_3,
            R.id.nzindT20_image_4,
            R.id.nzindT20_image_5,
            R.id.nzindT20_image_6
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
            R.drawable.tnzind2,
            R.drawable.tnzind1,
            R.drawable.tnzind3,
            R.drawable.tnzind4,
            R.drawable.tnzind5,
            R.drawable.tnzind6,
            R.drawable.tnzind7
        )

        val imageViewIds = listOf(
            R.id.nzindT20_featured_image,
            R.id.nzindT20_image_1,
            R.id.nzindT20_image_2,
            R.id.nzindT20_image_3,
            R.id.nzindT20_image_4,
            R.id.nzindT20_image_5,
            R.id.nzindT20_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
