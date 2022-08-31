package com.example.e_banking_app.data.model.balance

import com.example.e_banking_app.intefaces.JSONConvertible

data class BalanceItem(
    val id: String,
    val date: String,
    val id_customer: String,
    val message: String,
    val money: String,
    val total_money: String,
) : JSONConvertible
