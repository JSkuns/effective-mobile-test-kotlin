package com.example.feature_courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feature_courses.databinding.FragmentCoursesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CoursesViewModel by viewModel()

    private lateinit var adapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupClickListeners()

        viewModel.loadCourses()
    }

    private fun setupRecyclerView() {
        adapter = CoursesAdapter { course ->
            viewModel.toggleLike(course)
        }

        binding.rvCourses.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCourses.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            adapter.submitList(courses)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Можно добавить ProgressBar
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.ivFilter.setOnClickListener {
            viewModel.sortByDate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}