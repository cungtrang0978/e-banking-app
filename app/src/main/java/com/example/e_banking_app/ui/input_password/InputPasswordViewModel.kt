package com.example.e_banking_app.ui.input_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R

class InputPasswordViewModel : ViewModel() {
    private val _inputPasswordFormState = MutableLiveData<InputPasswordFormState>()
    val inputPasswordFormState: LiveData<InputPasswordFormState> = _inputPasswordFormState


    fun dataChanged(password: String, confirmPassword: String) {
        val valid = password == confirmPassword
        _inputPasswordFormState.value =
            InputPasswordFormState(
                passwordError = if (valid) null else R.string.error_message_password_do_not_match,
                isValid = valid
            )
    }
}