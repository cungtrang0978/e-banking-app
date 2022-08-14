package com.example.e_banking_app.ui.change_password

data class ChangePasswordFormState(
    val confirmPasswordError: Int? = null,
    val oldPasswordError: Int? = null,
    val isValid: Boolean = false,
)