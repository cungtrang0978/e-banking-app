package com.example.e_banking_app.data.model.input

import com.example.e_banking_app.intefaces.JSONConvertible

data class ChangePasswordInput(val oldPassword: String, val newPassword: String) : JSONConvertible