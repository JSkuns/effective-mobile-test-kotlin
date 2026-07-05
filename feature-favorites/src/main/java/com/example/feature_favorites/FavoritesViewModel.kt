package com.example.feature_favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database.dao.CourseDao
import com.example.core_database.entity.CourseEntity
import com.example.core_network.model.Course
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val courseDao: CourseDao
) : ViewModel() {

    private val _favoriteCourses = MutableLiveData<List<Course>>()
    val favoriteCourses: LiveData<List<Course>> = _favoriteCourses

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        _isLoading.value = true
        viewModelScope.launch {
            courseDao.getFavoriteCourses().collectLatest { entities ->
                _favoriteCourses.value = entities.map { entity ->
                    Course(
                        id = entity.id,
                        title = entity.title,
                        text = entity.text,
                        price = entity.price,
                        rate = entity.rate,
                        startDate = entity.startDate,
                        hasLike = true,
                        publishDate = entity.publishDate
                    )
                }
                _isLoading.value = false
            }
        }
    }

    fun removeFromFavorites(course: Course) {
        viewModelScope.launch {
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
        }
    }
}