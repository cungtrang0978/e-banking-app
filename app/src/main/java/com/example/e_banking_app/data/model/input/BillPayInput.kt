package com.example.e_banking_app.data.model.input

import com.example.e_banking_app.intefaces.JSONConvertible

data class BillPayInput(val id: String, val token: String? = null) : JSONConvertible
