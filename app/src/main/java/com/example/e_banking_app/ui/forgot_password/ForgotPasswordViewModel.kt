package com.example.e_banking_app.ui.forgot_password

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.base.BaseResult
import com.example.e_banking_app.data.model.input.ForgotPasswordInput
import com.example.e_banking_app.data.repository.AuthRepository

class ForgotPasswordViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _forgotPasswordFormState = MutableLiveData<ForgotPasswordFormState>()
    val forgotPasswordFormState: LiveData<ForgotPasswordFormState> = _forgotPasswordFormState

    private val _result = MutableLiveData<BaseResult<Int>>()
    val result: LiveData<BaseResult<Int>> = _result


    fun dataChanged(phoneNumber: String, email: String, identityNumber: String) {
        if (!isPhoneNumberValid(phoneNumber)) {
            _forgotPasswordFormState.value =
                ForgotPasswordFormState(phoneNumberError = R.string.invalid_phone_number)
        } else if (!isEmailValid(email)) {
            _forgotPasswordFormState.value =
                ForgotPasswordFormState(phoneNumberError = R.string.invalid_email)
        } else if (identityNumber.isBlank()) {
            _forgotPasswordFormState.value =
                ForgotPasswordFormState(identityNumber = R.string.invalid_identity)
        } else {
            _forgotPasswordFormState.value = ForgotPasswordFormState(isDateValid = true)
        }
    }

    // A placeholder phoneNumber validation check
    private fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.length == 10
    }

    fun submit(phoneNumber: String, mail: String, identityNumber: String) {
        authRepository.sendForgotPasswordMail(
            ForgotPasswordInput(phoneNumber, mail, identityNumber),
            onSuccess = {
                _result.value = BaseResult(success = R.string.send_forgot_password_mail_succesfully)
            },
            onFailure = {
                _result.value =
                    BaseResult(error = R.string.fail_to_send_forgot_password_mail)

            },
        )
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}