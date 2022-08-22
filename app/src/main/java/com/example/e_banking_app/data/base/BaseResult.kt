package com.example.e_banking_app.data.base

data class BaseResult<T>(val success: T? = null, val error: Int? = null)
