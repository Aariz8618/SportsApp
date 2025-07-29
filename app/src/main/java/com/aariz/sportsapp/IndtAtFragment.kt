package com.aariz.sportsapp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

class IndtAtFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.indtat, container, false)
        setUpImageViewClickListeners(view)
        return view
    }
    private fun setUpImageViewClickListeners(view: View) {
        val imageViewIds = listOf(
            R.id.att_featured_image, R.id.att_image_1, R.id.att_image_2,
            R.id.att_image_3, R.id.att_image_4, R.id.att_image_5,
            R.id.att_image_6, R.id.att_image_7, R.id.att_image_8,
            R.id.att_image_9, R.id.att_image_10, R.id.att_image_11,
            R.id.att_image_12, R.id.att_image_13, R.id.att_image_14
        )

        for (id in imageViewIds) {
            val imageView = view.findViewById<ImageView>(id)
            imageView.setOnClickListener {
                showFullScreenImage(it as ImageView)
            }
        }
    }

    private fun showFullScreenImage(imageView: ImageView) {
        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        dialog.setContentView(R.layout.dialog_fullscreen_image)
        
        val fullScreenImageView = dialog.findViewById<ImageView>(R.id.fullscreen_image)
        
        // Copy the image from the clicked ImageView
        fullScreenImageView.setImageDrawable(imageView.drawable)
        
        // Make dialog truly fullscreen
        val window = dialog.window
        window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            
            // Use modern Window Insets API instead of deprecated systemUiVisibility
            WindowCompat.setDecorFitsSystemWindows(this, false)
            val controller = WindowInsetsControllerCompat(this, decorView)
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        
        // Close dialog when clicked anywhere
        fullScreenImageView.setOnClickListener {
            dialog.dismiss()
        }
        
        // Also allow back button to close
        dialog.setOnCancelListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }
}
