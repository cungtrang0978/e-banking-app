package com.example.e_banking_app

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.e_banking_app.databinding.ActivityMainFlowBinding
import com.example.e_banking_app.utils.LanguageUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainFlowBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LanguageUtils.loadLocale(this)


        binding = ActivityMainFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main_flow)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_balance, R.id.navigation_profile
            )
        )
        R.string.action_back_to_home
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean =
//        when (item.itemId) {
//            android.R.id.home -> {
//                showDialog() // show your dialog here
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
}