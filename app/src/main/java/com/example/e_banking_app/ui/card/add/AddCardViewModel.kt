package com.example.e_banking_app.ui.card.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.input.CreateCardInput
import com.example.e_banking_app.data.repository.CardRepository

class AddCardViewModel(private val cardRepository: CardRepository) : ViewModel() {
    private val _addCardFormState = MutableLiveData<AddCardFormState>()
    val addCardFormState: LiveData<AddCardFormState> = _addCardFormState

    private val _addCardResult = MutableLiveData<AddCardResult>()
    val addCardResult: LiveData<AddCardResult> = _addCardResult

    fun createCard(createCardInput: CreateCardInput) {
        cardRepository.createCard(
            createCardInput,
            onSuccess = {
                _addCardResult.value = AddCardResult(success = R.string.create_card_successfully)
            },
            onFailure = {
                _addCardResult.value = AddCardResult(error = R.string.create_card_fail)
            },
        )
    }

    fun dataChanged(pin: String) {
        if (pin.isBlank() || pin.length < 6) {
            _addCardFormState.value = AddCardFormState(pinError = R.string.error_pin_empty)
        } else {
            _addCardFormState.value = AddCardFormState(isDataValid = true)
        }

    }
}