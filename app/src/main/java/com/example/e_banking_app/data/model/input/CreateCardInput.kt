package com.example.e_banking_app.data.model.input

import com.example.e_banking_app.intefaces.JSONConvertible

data class CreateCardInput(val pin: String, val token: String, val status: String = "enable") :
    JSONConvertible
