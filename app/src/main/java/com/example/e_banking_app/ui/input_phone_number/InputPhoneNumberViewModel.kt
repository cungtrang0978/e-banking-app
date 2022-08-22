package com.example.e_banking_app.ui.input_phone_number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.repository.AuthRepository

class InputPhoneNumberViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _inputPhoneNumberForm = MutableLiveData<InputPhoneNumberFormState>()
    val inputPhoneNumberFormState: LiveData<InputPhoneNumberFormState> = _inputPhoneNumberForm

    private val _inputPhoneNumberState = MutableLiveData<InputPhoneNumberState>()
    val inputPhoneNumberState: LiveData<InputPhoneNumberState> = _inputPhoneNumberState

    fun dataChanged(phoneNumber: String) {
        if (!isPhoneNumberValid(phoneNumber)) {
            _inputPhoneNumberForm.value =
                InputPhoneNumberFormState(phoneNumberError = R.string.invalid_phone_number)
        } else {
            _inputPhoneNumberForm.value = InputPhoneNumberFormState(isDateValid = true)
        }
    }

    // A placeholder phoneNumber validation check
    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.length == 10
    }

    fun submit(phoneNumber: String) {
        authRepository.checkPhoneNumber(
            phoneNumber,
            onSuccess = {
                _inputPhoneNumberState.value = InputPhoneNumberState(success = it)
            },
            onFailure = {
                _inputPhoneNumberState.value =
                    InputPhoneNumberState(error = R.string.fail_to_check_phone_number)

            },
        )
    }
}