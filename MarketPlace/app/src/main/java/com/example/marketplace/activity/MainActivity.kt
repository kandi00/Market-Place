package com.example.marketplace.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.marketplace.R
import com.example.marketplace.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var navController : NavController
    private lateinit var navHostFragment : NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var toolBar: MaterialToolbar
    lateinit var searchView : SearchView
    lateinit var searchIcon: MenuItem
    lateinit var profileIcon: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeElements()

        /**setup toolbar*/
        appBarConfiguration = AppBarConfiguration(topLevelDestinationIds = setOf(R.id.timelineFragment, R.id.myMarketFragment, R.id.myFaresFragment),
                                                  fallbackOnNavigateUpListener = ::onSupportNavigateUp)
        toolBar.setupWithNavController(navController, appBarConfiguration)
        searchIcon.isVisible = false
        profileIcon.isVisible = false

        /**setup bottom navigation and toolbar visibility*/
        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigation.visibility =  if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.forgotPasswordFragment) {
                View.GONE
            } else {
                View.VISIBLE
            }
            toolBar.visibility = bottomNavigation.visibility
        }
    }

    private fun initializeElements(){
        bottomNavigation = binding.bottomNavigation
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)
        toolBar = findViewById(R.id.topAppBar)
        searchView = binding.searchView
        searchIcon = toolBar.menu.findItem(R.id.appBar_search)
        profileIcon = toolBar.menu.findItem(R.id.appBar_profile_icon)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}


