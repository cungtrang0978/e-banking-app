package com.example.e_banking_app.ui.transfer.interbank_transfer

data class InterbankTransferFormState(
    val accountNumberError: Int? = null,
    val amountError: Int? = null,
    val messageError: Int? = null,
    val isValid: Boolean = false,
)
