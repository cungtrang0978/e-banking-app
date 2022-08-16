package com.example.e_banking_app.data.model.passbook

import com.example.e_banking_app.intefaces.JSONConvertible

data class PassbookCategory(
    val id_category_passbook: String,
    val name_passbook: String,
    val period: String,
    val interest_rate: String,
    val description: String,
    val state: String,
) : JSONConvertible
