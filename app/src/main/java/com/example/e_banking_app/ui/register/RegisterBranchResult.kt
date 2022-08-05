package com.example.e_banking_app.ui.register

import com.example.e_banking_app.data.model.Branch

data class RegisterBranchResult(
    val success: List<Branch>? = null,
    val error: Int? = null
)
