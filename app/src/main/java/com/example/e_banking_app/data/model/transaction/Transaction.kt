package com.example.e_banking_app.data.model.transaction

import android.os.Parcelable
import com.example.e_banking_app.intefaces.JSONConvertible
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val to_name_customer: String,
    val to_account_number_customer: String,
    val from_account_number_customer: String = "",
    val id_tranfer: String,
    val money: String,
    val time_tranfer: String,
    val message: String,
    val to_bank_name: String,
) : JSONConvertible, Parcelable
