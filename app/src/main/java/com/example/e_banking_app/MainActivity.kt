package com.example.e_banking_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
//        val f: Fragment? = supportFragmentManager.findFragmentById(R.id.login)
//        val currentFragment = navHostFragment.childFragmentManager.fragments[0]
//        if (f == currentFragment) return false
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}