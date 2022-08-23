package com.example.e_banking_app.ui.forgot_password

data class ForgotPasswordFormState(
    val phoneNumberError: Int? = null,
    val mailError: Int? = null,
    val identityNumber: Int? = null,
    val isDateValid: Boolean = false
)
