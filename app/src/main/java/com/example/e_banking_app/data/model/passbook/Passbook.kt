package com.example.e_banking_app.data.model.passbook

import com.example.e_banking_app.intefaces.JSONConvertible

data class Passbook(
    val id_passbook: String,
    val id_customer: String,
    val money: String,
    val name_passbook: String = "",
    val id_category_passbook: String,
    val created_at: String,
    val updated_at: String?,
    val interest_rate: String = "",
    val due: Boolean,
    val state: String,
) : JSONConvertible