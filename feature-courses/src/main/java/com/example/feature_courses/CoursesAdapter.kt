package com.example.feature_courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core_network.model.Course
import com.example.feature_courses.databinding.ItemCourseBinding
import com.example.feature_courses.R

class CoursesAdapter(
    private val onLikeClick: (Course) -> Unit
) : ListAdapter<Course, CoursesAdapter.CourseViewHolder>(CourseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseViewHolder(binding, onLikeClick)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CourseViewHolder(
        private val binding: ItemCourseBinding,
        private val onLikeClick: (Course) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            // Заголовок и описание
            binding.tvTitle.text = course.title
            binding.tvText.text = course.text

            // Цена с знаком валюты
            binding.tvPrice.text = "${course.price} ₽"

            // Рейтинг на баннере (используем tvRateOnBanner из XML)
            binding.tvRateOnBanner.text = course.rate.toString()

            // Дата на баннере (используем tvDateOnBanner из XML)
            binding.tvDateOnBanner.text = formatDate(course.startDate)

            // Закладка избранного: меняем иконку в зависимости от hasLike
            binding.ivLike.setImageResource(
                if (course.hasLike) R.drawable.ic_bookmark_filled
                else R.drawable.ic_bookmark_border
            )

            // Обработка клика на закладку
            binding.ivLike.setOnClickListener {
                onLikeClick(course)
            }
        }

        // Форматирование даты: "2024-05-22" → "22 Мая 2024"
        private fun formatDate(dateString: String): String {
            return try {
                val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                val outputFormat = java.text.SimpleDateFormat("dd MMMM yyyy", java.util.Locale("ru"))
                val date = inputFormat.parse(dateString)
                outputFormat.format(date!!)
            } catch (e: Exception) {
                dateString
            }
        }
    }

    class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
}