package com.example.e_banking_app.data.model

data class BaseApiResponse<T>(
    val query_err: Boolean,
    val err_detail: String,
    val result: T
)
