package com.example.e_banking_app.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.input.RegisterInput
import com.example.e_banking_app.data.repository.AuthRepository
import com.example.e_banking_app.data.repository.MasterRepository

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val masterRepository: MasterRepository
) : ViewModel() {
    init {
        init()
    }

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    private val _registerBranchResult = MutableLiveData<RegisterBranchResult>()
    val registerBranchResult: LiveData<RegisterBranchResult> = _registerBranchResult

    fun dataChanged(
        fullName: String,
        dob: String,
        address: String,
        email: String,
        identity: String
    ) {
        if (fullName.isBlank()) {
            _registerForm.value = RegisterFormState(fullNameError = R.string.invalid_full_name)
        } else if (dob.isBlank()) {
            _registerForm.value = RegisterFormState(dobError = R.string.invalid_dob)
        } else if (address.isBlank()) {
            _registerForm.value = RegisterFormState(addressError = R.string.invalid_address)
        } else if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else if (identity.isBlank()) {
            _registerForm.value = RegisterFormState(identityError = R.string.invalid_identity)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun register(registerInput: RegisterInput) {
        authRepository.register(
            registerInput,
            onSuccess = {
                _registerResult.value = RegisterResult(success = "Dang ky thanh cong")
            },
            onFailure = {
                _registerResult.value = RegisterResult(error = R.string.fail_to_register)
            },
        )

    }

    private fun init() {
        fetchBranchList()
    }

    private fun fetchBranchList() {
        masterRepository.fetchBranchList(
            onSuccess = {
                _registerBranchResult.value = RegisterBranchResult(success = it)
            },
            onFailure = {
                _registerBranchResult.value =
                    RegisterBranchResult(error = R.string.fail_to_fetch_branch_list)

            }
        )
    }
}