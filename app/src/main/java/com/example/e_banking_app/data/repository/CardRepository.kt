package com.example.e_banking_app.data.repository

import com.example.e_banking_app.data.data_source.CardDataSource
import com.example.e_banking_app.data.model.card.Card
import com.example.e_banking_app.data.model.input.CreateCardInput

class CardRepository(private val cardDataSource: CardDataSource) {

    fun createCard(
        createCardInput: CreateCardInput,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        cardDataSource.createCard(createCardInput, onSuccess, onFailure)
    }

    fun getCardList(
        token: String,
        onSuccess: (List<Card>) -> Unit,
        onFailure: () -> Unit,
    ) {
        cardDataSource.getCardList(token, onSuccess, onFailure)
    }
}