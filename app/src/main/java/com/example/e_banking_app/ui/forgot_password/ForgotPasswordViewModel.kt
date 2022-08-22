package com.example.e_banking_app.ui.forgot_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.base.BaseResult
import com.example.e_banking_app.data.repository.AuthRepository
import com.example.e_banking_app.ui.input_phone_number.InputPhoneNumberState

class ForgotPasswordViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _forgotPasswordFormState = MutableLiveData<ForgotPasswordFormState>()
    val forgotPasswordFormState: LiveData<ForgotPasswordFormState> = _forgotPasswordFormState

    private val _result = MutableLiveData<BaseResult<Int>>()
    val result: LiveData<BaseResult<Int>> = _result


    fun dataChanged(phoneNumber: String) {
        if (!isPhoneNumberValid(phoneNumber)) {
            _forgotPasswordFormState.value =
                ForgotPasswordFormState(phoneNumberError = R.string.invalid_phone_number)
        } else {
            _forgotPasswordFormState.value = ForgotPasswordFormState(isDateValid = true)
        }
    }

    // A placeholder phoneNumber validation check
    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.length == 10
    }
    fun submit(phoneNumber: String) {
        authRepository.sendForgotPasswordMail(
            phoneNumber,
            onSuccess = {
                _result.value = BaseResult(success = R.string.send_forgot_password_mail_succesfully)
            },
            onFailure = {
                _result.value =
                    BaseResult(error = R.string.fail_to_send_forgot_password_mail)

            },
        )
    }
}