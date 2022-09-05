package com.example.e_banking_app.data.model.input

import com.example.e_banking_app.intefaces.JSONConvertible

data class WithdrawPassbookInput(
    val token: String? = null,
    val id_passbook: String,
) : JSONConvertible
