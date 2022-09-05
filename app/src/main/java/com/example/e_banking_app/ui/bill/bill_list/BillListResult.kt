package com.example.e_banking_app.ui.bill.bill_list

import com.example.e_banking_app.data.model.bill.BillItem

data class BillListResult(
    val success: List<BillItem>? = null,
    val error: Int? = null
)
