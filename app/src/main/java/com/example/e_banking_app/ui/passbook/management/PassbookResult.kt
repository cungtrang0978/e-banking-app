package com.example.e_banking_app.ui.passbook.management

import com.example.e_banking_app.data.model.passbook.Passbook

data class PassbookResult(val success: List<Passbook>? = null, val error: Int? = null)
