package com.example.e_banking_app.data.model.card

data class Card(
    val id_card: String,
    val pin: String,
    val status: String,
    val created_at: String,
    val updated_at: String,
    val expired_at: String,
    val state: String,
    val id_customer: String,
) {
}