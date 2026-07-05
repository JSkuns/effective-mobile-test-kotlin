package com.example.feature_favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_network.model.Course
import com.example.feature_favorites.databinding.ItemFavoriteCourseBinding

class FavoritesAdapter(
    private val onRemoveClick: (Course) -> Unit
) : ListAdapter<Course, FavoritesAdapter.FavoriteViewHolder>(FavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding, onRemoveClick)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavoriteViewHolder(
        private val binding: ItemFavoriteCourseBinding,
        private val onRemoveClick: (Course) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            binding.tvTitle.text = course.title
            binding.tvText.text = course.text
            binding.tvPrice.text = course.price
            binding.tvRate.text = course.rate.toString()
            binding.tvStartDate.text = "Начало: ${course.startDate}"

            // Иконка like всегда заполнена (зелёная)
            binding.ivLike.setImageResource(android.R.drawable.btn_star_big_on)

            // Обработка клика на like (удаление из избранного)
            binding.ivLike.setOnClickListener {
                onRemoveClick(course)
            }
        }
    }

    class FavoriteDiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
}