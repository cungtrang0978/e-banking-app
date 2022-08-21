package com.example.e_banking_app

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.e_banking_app.utils.ContextUtil
import java.util.*


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("vi")
// Call this on the main thread as it may require Activity.restart()


        val name = ContextUtil.updateLocale(
            baseContext,
            Locale("vi")
        ).baseContext.getString(R.string.title_home)

//        createConfigurationContext(config)
//        resources.updateConfiguration(config, resources.displayMetrics)

//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
//
//        else
//            config.locale = locale
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
//        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onSupportNavigateUp(): Boolean {
//        val f: Fragment? = supportFragmentManager.findFragmentById(R.id.login)
//        val currentFragment = navHostFragment.childFragmentManager.fragments[0]
//        if (f == currentFragment) return false

        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}