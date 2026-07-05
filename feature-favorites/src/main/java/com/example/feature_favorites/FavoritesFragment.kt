package com.example.feature_favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feature_favorites.databinding.FragmentFavoritesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = FavoritesAdapter { course ->
            viewModel.removeFromFavorites(course)
            Toast.makeText(requireContext(), "Удалено из избранного", Toast.LENGTH_SHORT).show()
        }

        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.favoriteCourses.observe(viewLifecycleOwner) { courses ->
            adapter.submitList(courses)

            // Показываем/скрываем сообщение об отсутствии избранных курсов
            if (courses.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvFavorites.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvFavorites.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

