package com.example.e_banking_app.data.model.input

import com.example.e_banking_app.intefaces.JSONConvertible
import com.google.gson.annotations.SerializedName


data class LoginInput(
    @SerializedName("phone")
    val username: String,
    val password: String,
) : JSONConvertible
