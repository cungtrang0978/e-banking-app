package com.example.e_banking_app.ui.transfer.internal_transfer

import com.example.e_banking_app.data.model.transaction.Transaction

data class InternalTransferResult(
    val success: Transaction? = null,
    val error: Int? = null
)
