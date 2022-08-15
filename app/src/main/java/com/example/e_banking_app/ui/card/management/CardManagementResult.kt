package com.example.e_banking_app.ui.card.management

import com.example.e_banking_app.data.model.card.Card

data class CardManagementResult(val success: List<Card>? = null, val error: Int? = null)
