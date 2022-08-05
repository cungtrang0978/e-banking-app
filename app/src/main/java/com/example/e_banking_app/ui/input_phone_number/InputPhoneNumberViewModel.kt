package com.example.e_banking_app.ui.input_phone_number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R

class InputPhoneNumberViewModel : ViewModel() {
    private val _inputPhoneNumberForm = MutableLiveData<InputPhoneNumberFormState>()
    val inputPhoneNumberFormState: LiveData<InputPhoneNumberFormState> = _inputPhoneNumberForm

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
}