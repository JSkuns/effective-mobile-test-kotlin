package com.example.feature_courses.di

import com.example.feature_courses.CoursesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coursesModule = module {
    viewModel { CoursesViewModel(get()) }
}