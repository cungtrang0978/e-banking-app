package com.example.e_banking_app.data.model.input

import android.os.Parcelable
import com.example.e_banking_app.data.model.Gender
import com.example.e_banking_app.intefaces.JSONConvertible
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterInput(
    @SerializedName("phone")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String = "",
    @SerializedName("confirm_password")
    val confirmPassword: String = "",
    @SerializedName("name")
    val fullName: String = "",
    val dob: String = "",
    val gender: Gender = Gender.male,
    @SerializedName("address")
    val address: String = "",
    @SerializedName("mail")
    val email: String = "",
    //TODO: remove initial number later
    val id_branch: Int = 1,
    val id_card: String = "",
    val citizen_identity_card: String = "",
    val money: Int = 0,
    //TODO: remove initial number later
    val age: Int = 19,
) : Parcelable, JSONConvertible
