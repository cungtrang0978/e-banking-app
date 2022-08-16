package com.example.e_banking_app.ui.passbook.category

import com.example.e_banking_app.data.model.passbook.PassbookCategory

data class PassbookCategoryListState(
    val success: List<PassbookCategory>? = null,
    val error: Int? = null
)
