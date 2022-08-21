package com.example.e_banking_app.ui.passbook.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.input.PassbookInput
import com.example.e_banking_app.data.repository.PassbookRepository

class AddPassbookViewModel(private val passbookRepository: PassbookRepository) : ViewModel() {
    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> = _isValid

    private val _state = MutableLiveData<AddPassbookState>()
    val state: LiveData<AddPassbookState> = _state

    fun onChanged(amount: String) {
        _isValid.value = amount.isNotBlank()
    }

    fun submit(passbookInput: PassbookInput) {
        passbookRepository.addPassbook(passbookInput, onSuccess = {
            _state.value = AddPassbookState(success = R.string.add_passbook_success)
        },
            onFailure = {
                _state.value = AddPassbookState(success = R.string.add_passbook_error)

            })
    }
}