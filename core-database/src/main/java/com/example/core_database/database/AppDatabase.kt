package com.example.core_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_database.dao.CourseDao
import com.example.core_database.entity.CourseEntity

@Database(entities = [CourseEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}