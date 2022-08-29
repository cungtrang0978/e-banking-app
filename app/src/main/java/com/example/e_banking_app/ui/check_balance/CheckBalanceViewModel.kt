package com.example.e_banking_app.ui.check_balance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.repository.UserRepository
import com.example.e_banking_app.ui.info.InfoResult

class CheckBalanceViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _infoResult = MutableLiveData<InfoResult>()
    val infoResult: LiveData<InfoResult> = _infoResult

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        userRepository.getProfile(
            onSuccess = {
                _infoResult.value = InfoResult(success = it)
            },
            onFailure = {
                _infoResult.value = InfoResult(error = R.string.fail_to_fetch_profile)
            }
        )
    }
}