package com.adasoraninda.bottomnav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val videModel: SettingsViewModel
        val preferences = ThemePreferences(datastore)
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SettingsViewModel(preferences) as T
            }
        }

        videModel = ViewModelProvider(this, factory)[SettingsViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.findNavController()

        val appBArConfiguration =
            AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_chat, R.id.nav_settings))

        setupActionBarWithNavController(navController, appBArConfiguration)
        bottomNav.setupWithNavController(navController)

        videModel.theme.observe(this) {
            AppCompatDelegate.setDefaultNightMode(it)
        }
    }

}