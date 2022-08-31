package com.example.e_banking_app.ui.balance

import com.example.e_banking_app.data.model.balance.BalanceItem

data class BalanceResult(val success: List<BalanceItem>? = null, val error: Int? = null)
