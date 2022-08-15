package com.example.e_banking_app.ui.card.management.placeholder

data class PlaceholderItem(val id: String, val content: String, val details: String) {
    override fun toString(): String = content
}
