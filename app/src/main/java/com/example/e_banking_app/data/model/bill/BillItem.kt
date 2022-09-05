package com.example.e_banking_app.data.model.bill

import android.os.Parcelable
import com.example.e_banking_app.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillItem(
    val id_bill: String,
    val name_bill: String,
    val name_3rd: String,
    val money: String,
    val status: String,
    val created_at: String,
    val id_3rd: String,
) : Parcelable {
    val statusValue get():Int = if (status == "unpaid") R.string.unpaid else R.string.paid

    val billType
        get():BillType =
            when (id_3rd) {
                "1" -> BillType.Electric
                "2" -> BillType.Water
                "3" -> BillType.Internet
                else -> BillType.Electric
            }
}
