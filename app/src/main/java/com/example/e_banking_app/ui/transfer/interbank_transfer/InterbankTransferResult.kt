package com.example.e_banking_app.ui.transfer.interbank_transfer

import com.example.e_banking_app.data.model.transaction.Transaction

data class InterbankTransferResult(
    val success: Transaction? = null,
    val error: Int? = null
)