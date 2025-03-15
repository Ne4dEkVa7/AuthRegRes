package com.example.authregres.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.authregres.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeNavigation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_navigation_screen)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val settingsFragment = SettingsFragment()
        setCurrentFragment(homeFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.profile -> setCurrentFragment(profileFragment)
                R.id.settings -> setCurrentFragment(settingsFragment)
            }
            true
        }
    }
        private fun setCurrentFragment(fragment: Fragment)=
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.flFragment, fragment)
                commit()
            }
    }
