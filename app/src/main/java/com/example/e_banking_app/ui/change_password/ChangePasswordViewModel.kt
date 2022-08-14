package com.example.e_banking_app.ui.change_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.input.ChangePasswordInput
import com.example.e_banking_app.data.repository.UserRepository

class ChangePasswordViewModel(private val repository: UserRepository) : ViewModel() {
    private val _changePasswordFormState = MutableLiveData<ChangePasswordFormState>()
    val changePasswordFormState: LiveData<ChangePasswordFormState> = _changePasswordFormState

    private val _changePasswordResult = MutableLiveData<ChangePasswordResult>()
    val changePasswordResult: LiveData<ChangePasswordResult> = _changePasswordResult

    fun dataChanged(newPassword: String, confirmPassword: String) {
        val valid = newPassword == confirmPassword
        _changePasswordFormState.value =
            ChangePasswordFormState(
                confirmPasswordError = if (valid) null else R.string.error_message_password_do_not_match,
                isValid = valid
            )
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        repository.changePassword(
            ChangePasswordInput(oldPassword, newPassword),
            onSuccess = {
                _changePasswordResult.value =
                    ChangePasswordResult(success = R.string.change_password_success)
            },
            onFailure = {
                _changePasswordResult.value =
                    ChangePasswordResult(success = R.string.change_password_failure)
            }
        )
    }
}