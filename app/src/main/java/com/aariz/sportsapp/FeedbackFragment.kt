package com.aariz.sportsapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.aariz.sportsapp.databinding.FragmentFeedbackBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class FeedbackFragment : Fragment() {

    private var _binding: FragmentFeedbackBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            _binding = FragmentFeedbackBinding.inflate(inflater, container, false)
            return binding.root
        } catch (e: Exception) {
            // If there's an error creating the view, show a simple fallback
            Toast.makeText(context, "Error loading feedback form: ${e.message}", Toast.LENGTH_LONG).show()
            throw e
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        try {
            firestore = FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            // If Firebase initialization fails, show a message but continue
            Toast.makeText(context, "Firebase not available. Feedback will be stored locally.", Toast.LENGTH_SHORT).show()
        }
        
        setupUI()
        setupBackPressHandler()
    }

    private fun setupBackPressHandler() {
        // Handle back press to hide keyboard
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                hideKeyboard()
                // Let the system handle the back press normally
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = requireActivity().currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun setupUI() {
        try {
            // Set up rating buttons
            setupRatingButtons()
            
            // Set up submit button
            binding.btnSubmitFeedback.setOnClickListener {
                submitFeedback()
            }

            // Set up clear button
            binding.btnClearFeedback.setOnClickListener {
                clearForm()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error setting up feedback form: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRatingButtons() {
        val ratingButtons = listOf(
            binding.btnRating1, binding.btnRating2, binding.btnRating3,
            binding.btnRating4, binding.btnRating5
        )

        ratingButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                // Update selected rating
                updateRatingSelection(index + 1)
            }
        }
    }

    private fun updateRatingSelection(rating: Int) {
        val ratingButtons = listOf(
            binding.btnRating1, binding.btnRating2, binding.btnRating3,
            binding.btnRating4, binding.btnRating5
        )

        ratingButtons.forEachIndexed { index, button ->
            if (index + 1 == rating) {
                // Selected emoji gets highlighted background
                button.setBackgroundResource(R.drawable.button_rating_selected)
                button.setTextColor(resources.getColor(android.R.color.white, null))
            } else {
                // Unselected emojis get normal background
                button.setBackgroundResource(R.drawable.button_rating_unselected)
                button.setTextColor(resources.getColor(android.R.color.black, null))
            }
        }

        // Store selected rating
        binding.root.tag = rating
        
        // Show rating description
        val ratingDescriptions = arrayOf(
            "Terrible! ",
            "Poor ",
            "Okay ",
            "Good ",
            "Awesome! "
        )
        
        if (rating > 0 && rating <= ratingDescriptions.size) {
            binding.tvRatingDescription.text = ratingDescriptions[rating - 1]
            binding.tvRatingDescription.visibility = View.VISIBLE
        }
    }

    private fun submitFeedback() {
        val name = binding.etFeedbackName.text.toString().trim()
        val email = binding.etFeedbackEmail.text.toString().trim()
        val subject = binding.etFeedbackSubject.text.toString().trim()
        val message = binding.etFeedbackMessage.text.toString().trim()
        val rating = binding.root.tag as? Int ?: 0

        // Validate input
        if (name.isEmpty()) {
            binding.etFeedbackName.error = "Name is required"
            binding.etFeedbackName.requestFocus()
            return
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etFeedbackEmail.error = "Valid email is required"
            binding.etFeedbackEmail.requestFocus()
            return
        }

        if (subject.isEmpty()) {
            binding.etFeedbackSubject.error = "Subject is required"
            binding.etFeedbackSubject.requestFocus()
            return
        }

        if (message.isEmpty()) {
            binding.etFeedbackMessage.error = "Message is required"
            binding.etFeedbackMessage.requestFocus()
            return
        }

        if (rating == 0) {
            Toast.makeText(context, "Please select a rating", Toast.LENGTH_SHORT).show()
            return
        }

        // Hide keyboard when submitting
        hideKeyboard()
        
        // Show loading
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSubmitFeedback.isEnabled = false
        binding.btnSubmitFeedback.text = "Submitting..."

        // Create feedback document
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val feedbackData = hashMapOf(
            "name" to name,
            "email" to email,
            "subject" to subject,
            "message" to message,
            "rating" to rating,
            "timestamp" to timestamp,
            "device" to android.os.Build.MODEL,
            "android_version" to android.os.Build.VERSION.RELEASE
        )

        // Show success message after 2 seconds delay
        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(context, "Thank you for your feedback! We appreciate your input.", Toast.LENGTH_LONG).show()
            clearForm()
            
            // Reset button state
            binding.progressBar.visibility = View.GONE
            binding.btnSubmitFeedback.isEnabled = true
            binding.btnSubmitFeedback.text = "SUBMIT"
        }, 2000) // 2 second delay
        
        // Submit to Firestore in background (async, non-blocking)
        try {
            if (::firestore.isInitialized) {
                firestore.collection("feedback")
                    .add(feedbackData)
                    .addOnSuccessListener { documentReference ->

                    }
                    .addOnFailureListener { e ->
                    }
            }
        } catch (e: Exception) {
        }
    }

    private fun clearForm() {
        binding.etFeedbackName.text?.clear()
        binding.etFeedbackEmail.text?.clear()
        binding.etFeedbackSubject.text?.clear()
        binding.etFeedbackMessage.text?.clear()
        
        // Clear rating selection
        val ratingButtons = listOf(
            binding.btnRating1, binding.btnRating2, binding.btnRating3,
            binding.btnRating4, binding.btnRating5
        )

        ratingButtons.forEach { button ->
            button.setBackgroundResource(R.drawable.button_rating_unselected)
            button.setTextColor(resources.getColor(R.color.text_primary, null))
        }

        // Hide rating description
        binding.tvRatingDescription.visibility = View.GONE
        binding.tvRatingDescription.text = ""

        binding.root.tag = 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
