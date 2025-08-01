package com.aariz.sportsapp

import android.os.Bundle
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
        arguments?.let {
            imageResId = it.getInt(ARG_IMAGE_RES_ID)
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullscreenImageBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeableImageView.setImageResource(imageResId)
        binding.swipeableImageView.scaleType = ImageView.ScaleType.FIT_CENTER
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 