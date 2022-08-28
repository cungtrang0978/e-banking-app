package com.example.e_banking_app.utils

import java.text.NumberFormat
import java.util.*

class CurrencyUtils {
    companion object {
        fun format(amount: String): String {
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("VND")

            return format.format(Integer.parseInt(amount))
        }
    }
}