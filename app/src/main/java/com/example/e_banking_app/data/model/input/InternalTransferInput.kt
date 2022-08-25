package com.example.e_banking_app.data.model.input

import com.example.e_banking_app.intefaces.JSONConvertible

data class InternalTransferInput(
    val token: String? = null,
    val money: String,
    val to: String,
    val message: String,
) : JSONConvertible
