package com.example.marketplace.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.marketplace.R
import com.example.marketplace.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigation = binding.bottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)

        //hiding bottomNavigation in case of LoginFragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigation.visibility = if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.forgotPasswordFragment) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}


