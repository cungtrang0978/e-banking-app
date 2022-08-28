package com.example.e_banking_app.utils

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import java.util.*


@Suppress("DEPRECATION")
class LanguageUtils {
    private val lang = ""

    companion object {
        /* ------------------------------------- */
        private var myLocale: Locale? = null

        // Lưu ngôn ngữ đã cài đặt
        private fun saveLocale(lang: String?, activity: Activity) {
            val langPref = "Language"
            val prefs = activity.getSharedPreferences(
                "CommonPrefs",
                Activity.MODE_PRIVATE
            )
            val editor = prefs.edit()
            editor.putString(langPref, lang)
            editor.commit()
        }

        // Load lại ngôn ngữ đã lưu và thay đổi chúng
        fun loadLocale(activity: Activity) {
            val langPref = "Language"
            val prefs = activity.getSharedPreferences(
                "CommonPrefs",
                Activity.MODE_PRIVATE
            )
            val language = prefs.getString(langPref, "")
            changeLang(language, activity)
        }

        fun getLang(activity: Activity): String {
            val langPref = "Language"
            val prefs = activity.getSharedPreferences(
                "CommonPrefs",
                Activity.MODE_PRIVATE
            )
            val language = prefs.getString(langPref, "")
            if (language.isNullOrBlank()) {
                return Locale.getDefault().language
            }
            return language
        }

        // method phục vụ cho việc thay đổi ngôn ngữ.
        fun changeLang(lang: String?, activity: Activity) {
            Log.d("Locale", Locale.getDefault().language)
            if (lang.equals("", ignoreCase = true)) return
            myLocale = lang?.let { Locale(it) }
            saveLocale(lang, activity)
            myLocale?.let { Locale.setDefault(it) }
            Log.d("Locale", Locale.getDefault().language)

            val config = Configuration()

            config.setLocale(myLocale)
            activity.baseContext.resources.updateConfiguration(
                config,
                activity.baseContext.resources.displayMetrics
            )
        }
    }
}