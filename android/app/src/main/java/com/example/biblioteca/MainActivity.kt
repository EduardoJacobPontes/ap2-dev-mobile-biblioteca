package com.example.biblioteca

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, HomeFragment()).commit()

        bottomNav.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_profile -> selectedFragment = ProfileFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit()
            }
            true
        }
    }
}
