package com.aariz.sportsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentFullscreenImageBinding

class FullscreenImageFragment : Fragment() {
    
    private var _binding: FragmentFullscreenImageBinding? = null
    private val binding get() = _binding!!
    
    private var imageResId: Int = 0
    
    companion object {
        private const val ARG_IMAGE_RES_ID = "image_res_id"
        
        fun newInstance(imageResId: Int): FullscreenImageFragment {
            return FullscreenImageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_IMAGE_RES_ID, imageResId)
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            arguments?.let {
                imageResId = it.getInt(ARG_IMAGE_RES_ID)
            }
        } catch (e: Exception) {
            Log.e("FullscreenImageFragment", "Error getting image resource ID", e)
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            _binding = FragmentFullscreenImageBinding.inflate(inflater, container, false)
            return binding.root
        } catch (e: Exception) {
            Log.e("FullscreenImageFragment", "Error inflating layout", e)
            // Return a simple view as fallback
            return View(requireContext())
        }
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if (imageResId != 0) {
                binding.swipeableImageView.setImageResource(imageResId)
                binding.swipeableImageView.scaleType = ImageView.ScaleType.FIT_CENTER
            } else {
                Log.e("FullscreenImageFragment", "Invalid image resource ID")
            }
        } catch (e: Exception) {
            Log.e("FullscreenImageFragment", "Error setting image", e)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 