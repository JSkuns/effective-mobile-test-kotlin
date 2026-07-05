package com.example.feature_courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database.dao.CourseDao
import com.example.core_database.entity.CourseEntity
import com.example.core_network.client.NetworkClient
import com.example.core_network.model.Course
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CoursesViewModel(
    private val courseDao: CourseDao
) : ViewModel() {

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> = _courses

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var allCourses: List<Course> = emptyList()
    private var isSortedDescending = false

    fun loadCourses() {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                val response = NetworkClient.coursesApi.getCourses()
                allCourses = response.courses

                // Проверяем, какие курсы есть в избранном
                val favoriteCourseIds = courseDao.getFavoriteCourseIds()

                // Обновляем hasLike для курсов
                allCourses = allCourses.map { course ->
                    course.copy(hasLike = course.id in favoriteCourseIds)
                }

                _courses.value = allCourses
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки: ${e.message}"
                _isLoading.value = false
                _courses.value = emptyList()
            }
        }
    }

    fun sortByDate() {
        isSortedDescending = !isSortedDescending
        val sortedList = if (isSortedDescending) {
            allCourses.sortedByDescending { parseDate(it.publishDate) }
        } else {
            allCourses.sortedBy { parseDate(it.publishDate) }
        }
        _courses.value = sortedList
    }

    fun toggleLike(course: Course) {
        viewModelScope.launch {
            val isFavorite = course.id in courseDao.getFavoriteCourseIds()

            if (isFavorite) {
                // Удаляем из избранного
                val entity = CourseEntity(
                    id = course.id,
                    title = course.title,
                    text = course.text,
                    price = course.price,
                    rate = course.rate,
                    startDate = course.startDate,
                    publishDate = course.publishDate,
                    hasLike = false
                )
                courseDao.deleteCourse(entity)
            } else {
                // Добавляем в избранное
                val entity = CourseEntity(
                    id = course.id,
                    title = course.title,
                    text = course.text,
                    price = course.price,
                    rate = course.rate,
                    startDate = course.startDate,
                    publishDate = course.publishDate,
                    hasLike = true
                )
                courseDao.insertCourse(entity)
            }

            // Обновляем UI
            val favoriteCourseIds = courseDao.getFavoriteCourseIds()
            val updatedList = allCourses.map {
                it.copy(hasLike = it.id in favoriteCourseIds)
            }
            allCourses = updatedList
            _courses.value = updatedList
        }
    }

    private fun parseDate(dateString: String): Date {
        return try {
            val formats = listOf(
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()),
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()),
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            )

            formats.firstNotNullOfOrNull { format ->
                try {
                    format.parse(dateString)
                } catch (e: Exception) {
                    null
                }
            } ?: Date(0)
        } catch (e: Exception) {
            Date(0)
        }
    }
}