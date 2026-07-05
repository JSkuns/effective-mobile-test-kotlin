package com.example.effective_mobile_test_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.effective_mobile_test_android.databinding.ActivityMainBinding
import com.example.effective_mobile_test_android.main.AccountFragment
import com.example.feature_courses.CoursesFragment
import com.example.feature_favorites.FavoritesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Убираем черную полосу внизу
        window.navigationBarColor = getColor(R.color.background_surface)

        setupBottomNavigation()

        if (savedInstanceState == null) {
            loadFragment(CoursesFragment())
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loadFragment(CoursesFragment())
                    true
                }
                R.id.navigation_favorites -> {
                    loadFragment(FavoritesFragment())
                    true
                }
                R.id.navigation_account -> {
                    loadFragment(AccountFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}