package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aariz.sportsapp.adapter.ScheduleAdapter
import com.aariz.sportsapp.databinding.FragmentScheduleBinding
import com.aariz.sportsapp.viewmodel.ScheduleViewModel
import com.aariz.sportsapp.viewmodel.ScheduleViewModelFactory

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ScheduleViewModel
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        scheduleAdapter = ScheduleAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = scheduleAdapter
        }

        // Setup ViewModel
        viewModel = ViewModelProvider(this, ScheduleViewModelFactory()).get(ScheduleViewModel::class.java)

        // Observe LiveData
        viewModel.scheduleMatches.observe(viewLifecycleOwner, {
            scheduleAdapter.submitList(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.tvError.text = it
                binding.tvError.visibility = View.VISIBLE
            } else {
                binding.tvError.visibility = View.GONE
            }
        })

        // Fetch data
        viewModel.fetchSchedule()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
