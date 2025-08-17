package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class NzIndOdiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("NzIndOdiFragment", "onCreateView called - loading nzindodi.xml")
        return try {
            val view = inflater.inflate(R.layout.nzindodi, container, false)
            Log.d("NzIndOdiFragment", "nzindodi.xml layout inflated successfully")
            setUpImageViewClickListeners(view)
            view
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.nzindodi_featured_image,
            R.id.nzindodi_image_1,
            R.id.nzindodi_image_2,
            R.id.nzindodi_image_3,
            R.id.nzindodi_image_4,
            R.id.nzindodi_image_5,
            R.id.nzindodi_image_6
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
            R.drawable.nzindo1,
            R.drawable.nzindo4,
            R.drawable.nzindo5,
            R.drawable.nzindo3,
            R.drawable.nzindo2,
            R.drawable.nzindo6,
            R.drawable.nzindo7
        )

        val imageViewIds = listOf(
            R.id.nzindodi_featured_image,
            R.id.nzindodi_image_1,
            R.id.nzindodi_image_2,
            R.id.nzindodi_image_3,
            R.id.nzindodi_image_4,
            R.id.nzindodi_image_5,
            R.id.nzindodi_image_6
        )

        val position = imageViewIds.indexOf(clickedImageId)

        FullscreenImageActivity.start(requireContext(), images, position)
    }
}
