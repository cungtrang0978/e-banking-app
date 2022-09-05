package com.example.e_banking_app.data.model.bill

import com.example.e_banking_app.R

enum class BillType {
    Electric,
    Water,
    Internet,
}


fun BillType.getIconResource(): Int {
    return when (this) {
        BillType.Electric -> R.drawable.ic_electric
        BillType.Water -> R.drawable.ic_water
        BillType.Internet -> R.drawable.ic_wifi
    }
}