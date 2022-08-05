package com.example.e_banking_app.intefaces

import com.google.gson.Gson

interface JSONConvertible {
    fun toJSON(): String = Gson().toJson(this)
}

inline fun <reified T : JSONConvertible> String.toObject(): T = Gson().fromJson(this, T::class.java)