package com.example.e_banking_app.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult (
     val success:LoginResponse? = null,
     val error:Int? = null
)