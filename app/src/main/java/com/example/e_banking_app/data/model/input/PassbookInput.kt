package com.example.e_banking_app.data.model.input

import com.example.e_banking_app.intefaces.JSONConvertible

data class PassbookInput(
    val token: String? = null,
    val money: Int,
    val id_category_passbook: String,
) : JSONConvertible
