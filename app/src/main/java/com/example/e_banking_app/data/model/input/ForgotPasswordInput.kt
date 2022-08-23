package com.example.e_banking_app.data.model.input

import com.example.e_banking_app.intefaces.JSONConvertible

data class ForgotPasswordInput(
    val phone: String,
    val mail: String,
    val citizen_identity_card: String
) : JSONConvertible
