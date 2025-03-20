package com.example.authregres.Presentation.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.authregres.Presentation.Authorization
import com.example.authregres.Data.DBHelper
import com.example.authregres.R
import com.example.authregres.databinding.HomeNavigationScreenBinding
import com.example.authregres.Presentation.Home.fragments.CartFragment
import com.example.authregres.Presentation.Home.fragments.HomeFragment
import com.example.authregres.Presentation.Home.fragments.ProfileFragment
import com.example.authregres.Presentation.Home.fragments.SettingsFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView

class HomeNavigation : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: HomeNavigationScreenBinding
    private lateinit var userEmail: String
    private lateinit var userLogin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = HomeNavigationScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener(this)

        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        userLogin = sharedPreferences.getString("login", "") ?: ""
        userEmail = sharedPreferences.getString("email", "") ?: ""

        updateNavigationHeader(userLogin, userEmail)



        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> openFragment(HomeFragment())
                R.id.profile -> openProfileFragment()
                R.id.cart -> openFragment(CartFragment())
                R.id.settings -> openFragment(SettingsFragment())
            }
            true
        }
        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())
        binding.fab.setOnClickListener() {
            Toast.makeText(this, "+", Toast.LENGTH_LONG).show()
        }

    }

    fun updateNavigationHeader(login: String, email: String) {
        val headerView = binding.navigationView.getHeaderView(0)
        val headerLogin = headerView.findViewById<TextView>(R.id.drawer_header_login)
        val headerEmail = headerView.findViewById<TextView>(R.id.drawer_header_email)
        val headerImage = headerView.findViewById<ShapeableImageView>(R.id.drawer_header_image)

        headerLogin.text = login
        headerEmail.text = email

        val dbHelper = DBHelper(this, null)
        val bitmap = dbHelper.loadProfileImage(email)
        if (bitmap != null){
            headerImage.setImageBitmap(bitmap)
        } else {
            headerImage.setImageResource(R.drawable.icons_default_avatar)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> openFragment(HomeFragment())
            R.id.profile -> openProfileFragment()
            R.id.cart -> openFragment(CartFragment())
            R.id.settings -> openFragment(SettingsFragment())
            R.id.logout -> logoutUser()
        }
        return true
    }

    private fun openProfileFragment() {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val login = sharedPreferences.getString("login", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""

        val profileFragment = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString("login", login)
                putString("email", email)
            }
        }
        openFragment(profileFragment)
    }

    private fun logoutUser() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Подтверждение")
            setMessage("Вы уверены что хотите выйти из аккаунта?")
            setPositiveButton("Да") { dialog, which ->
                Toast.makeText(this@HomeNavigation, "Выходим из аккаунта...", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@HomeNavigation, Authorization::class.java))
                finish()
            }
            setNegativeButton("Отмена"){ dialog, which ->
                dialog.cancel()
            }
            show()
        }
    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flFragment, fragment)
        fragmentTransaction.commit()
    }
}
