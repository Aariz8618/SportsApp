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
import com.aariz.sportsapp.databinding.FragmentReportIssueBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ReportIssueFragment : Fragment() {

    private var _binding: FragmentReportIssueBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            _binding = FragmentReportIssueBinding.inflate(inflater, container, false)
            return binding.root
        } catch (e: Exception) {
            // If there's an error creating the view, show a simple fallback
            Toast.makeText(context, "Error loading report form: ${e.message}", Toast.LENGTH_LONG).show()
            throw e
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            firestore = FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            // If Firebase initialization fails, show a message but continue
            Toast.makeText(context, "Firebase not available. Report will be stored locally.", Toast.LENGTH_SHORT).show()
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
            // Set up submit button
            binding.btnSubmit.setOnClickListener {
                submitReport()
            }

            // Set up clear button
            binding.btnClear.setOnClickListener {
                clearForm()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error setting up report form: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun submitReport() {
        val category = binding.spinnerCategory.selectedItem?.toString() ?: ""
        val description = binding.etIssueDescription.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        // Validate input
        if (category.isEmpty() || category.equals("Select Category")) {
            Toast.makeText(requireContext(), "Please select a category", Toast.LENGTH_SHORT).show()
            return
        }

        if (description.isEmpty()) {
            binding.etIssueDescription.error = "Description is required"
            binding.etIssueDescription.requestFocus()
            return
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Please enter a valid email address"
            binding.etEmail.requestFocus()
            return
        }

        // Clear any previous errors
        binding.etIssueDescription.error = null
        binding.etEmail.error = null

        // Hide keyboard when submitting
        hideKeyboard()

        // Show loading
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSubmit.isEnabled = false
        binding.btnSubmit.text = "Submitting..."


        // Prepare data for Firestore
        val reportData = hashMapOf(
            "category" to category,
            "description" to description,
            "email" to email.ifEmpty { "Not provided" },
            "timestamp" to FieldValue.serverTimestamp(),
            "device" to android.os.Build.MODEL,
            "android_version" to android.os.Build.VERSION.RELEASE
        )

        if (::firestore.isInitialized) {
            firestore.collection("reports")
                .add(reportData)
                .addOnSuccessListener {
                    Handler(Looper.getMainLooper()).postDelayed({
                        Toast.makeText(requireContext(), "Report submitted successfully!", Toast.LENGTH_LONG).show()
                        clearForm()
                        binding.progressBar.visibility = View.GONE
                        binding.btnSubmit.isEnabled = true
                        binding.btnSubmit.text = "Submit Report"
                    }, 2000)
                }
                .addOnFailureListener { e ->
                    binding.progressBar.visibility = View.GONE
                    binding.btnSubmit.isEnabled = true
                    binding.btnSubmit.text = "Submit Report"
                    Toast.makeText(requireContext(), "Failed to submit report. Try again later.", Toast.LENGTH_LONG).show()
                }
        }

    }

    private fun clearForm() {
        binding.etIssueDescription.text?.clear()
        binding.etEmail.text?.clear()
        binding.spinnerCategory.setSelection(0)

        // Clear any error messages
        binding.etIssueDescription.error = null
        binding.etEmail.error = null

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}