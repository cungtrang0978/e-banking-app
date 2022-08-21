package com.example.e_banking_app.data.model.user

data class User(
    val id_person: String,
    val name: String,
    val citizen_identity_card: String,
    val mail: String,
    val phone: String,
    val address: String,
    val age: String,
    val money: String,
    val created_at: String,
    val updated_at: String,
    val id_branch: String,
    val state: String,
)
