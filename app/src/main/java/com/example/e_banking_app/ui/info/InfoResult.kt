package com.example.e_banking_app.ui.info

import com.example.e_banking_app.data.model.user.User

data class InfoResult(
    val success: User? = null,
    val error: Int? = null,
)
