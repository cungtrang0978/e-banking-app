package com.example.e_banking_app.data.model.input

import com.example.e_banking_app.intefaces.JSONConvertible

data class InterbankTransferInput(
    val token: String? = null,
    val money: String,
    val id_bank: String,
    val account_number: String,
    val message: String,
) : JSONConvertible
