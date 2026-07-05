package com.example.core_database.di

import androidx.room.Room
import com.example.core_database.dao.CourseDao
import com.example.core_database.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "courses_database"
        ).build()
    }

    single { get<AppDatabase>().courseDao() }
}