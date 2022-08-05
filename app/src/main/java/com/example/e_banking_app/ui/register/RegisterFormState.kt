package com.example.e_banking_app.ui.register

data class RegisterFormState(
    val fullNameError: Int? = null,
    val dobError: Int? = null,
    val addressError: Int? = null,
    val emailError: Int? = null,
    val identityError: Int? = null,
    val isDataValid: Boolean = false,
)
