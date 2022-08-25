package com.example.e_banking_app.data.model.bank

import com.example.e_banking_app.intefaces.JSONConvertible

data class Bank(
    val id_bank: String,
    val name_bank: String,
    val state: String,
) : JSONConvertible
