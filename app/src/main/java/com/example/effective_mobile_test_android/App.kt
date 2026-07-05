package com.example.effective_mobile_test_android

import android.app.Application
import com.example.core_database.di.databaseModule
import com.example.feature_courses.di.coursesModule
import com.example.feature_favorites.di.favoritesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                databaseModule,
                coursesModule,
                favoritesModule
            )
        }
    }
}