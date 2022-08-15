package com.example.e_banking_app.utils

import android.content.Context

abstract class AuthUtils {
    companion object {
        fun getToken(context: Context?): String {
            val sharedPref =
                context?.getSharedPreferences(
                    "access_token", Context.MODE_PRIVATE
                )
                    ?: return ""
            return sharedPref.getString(
                "access_token",
                ""
            ) ?: ""
        }
    }
}