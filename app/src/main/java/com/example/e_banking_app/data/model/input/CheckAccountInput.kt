package com.example.e_banking_app.data.model.input

import com.example.e_banking_app.intefaces.JSONConvertible

data class CheckAccountInput(
    val id_bank: String = "1",
    val account_number: String
) : JSONConvertible
