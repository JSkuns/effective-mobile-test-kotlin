package com.example.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_database.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM courses WHERE hasLike = 1")
    fun getFavoriteCourses(): Flow<List<CourseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    @Delete
    suspend fun deleteCourse(course: CourseEntity)

    @Query("DELETE FROM courses")
    suspend fun deleteAllCourses()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCourses(courses: List<CourseEntity>)

    @Query("SELECT EXISTS(SELECT 1 FROM courses WHERE id = :courseId)")
    suspend fun isFavorite(courseId: Int): Boolean

    @Query("SELECT id FROM courses WHERE hasLike = 1")
    suspend fun getFavoriteCourseIds(): List<Int>
}