package com.example.e_banking_app.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import java.util.*


@Suppress("DEPRECATION")
class ContextUtil(base: Context) : ContextWrapper(base) {

    companion object {

        fun updateLocale(context: Context, localeToSwitchTo: Locale): ContextWrapper {
            var newContext = context
            val resources: Resources = newContext.resources
            val configuration: Configuration = resources.configuration

            val localeList = LocaleList(localeToSwitchTo)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                newContext = newContext.createConfigurationContext(configuration)
            } else {
                resources.updateConfiguration(configuration, resources.displayMetrics)
            }

            return ContextUtil(newContext)
        }

    }

}