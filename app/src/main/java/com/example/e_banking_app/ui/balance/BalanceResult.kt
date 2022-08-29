package com.example.e_banking_app.ui.balance

import com.example.e_banking_app.data.model.transaction.Transaction

data class BalanceResult(val success: List<Transaction>? = null, val error: Int? = null)
