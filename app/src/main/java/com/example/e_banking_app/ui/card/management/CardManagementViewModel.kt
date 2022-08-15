package com.example.e_banking_app.ui.card.management

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.repository.CardRepository

class CardManagementViewModel(private val cardRepository: CardRepository) : ViewModel() {
    private val _cardManagementResult = MutableLiveData<CardManagementResult>()
    val cardManagementResult: LiveData<CardManagementResult> = _cardManagementResult


    fun fetchCardList(token: String) {
        cardRepository.getCardList(token,
            onSuccess = {
                _cardManagementResult.value = CardManagementResult(success = it)
            },
            onFailure = {
                _cardManagementResult.value =
                    CardManagementResult(error = R.string.fetch_card_list_error)
            })
    }
}