package com.example.e_banking_app.ui.passbook.management

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.repository.PassbookRepository

class PassbookViewModel(private val passbookRepository: PassbookRepository) : ViewModel() {
    private val _passbookResult = MutableLiveData<PassbookResult>()
    val passbookResult: LiveData<PassbookResult> = _passbookResult

    fun fetchPassbookList() {
        passbookRepository.getPassbookList(
            onSuccess = {
                _passbookResult.value = PassbookResult(success = it)
            },
            onFailure = {
                _passbookResult.value =
                    PassbookResult(error = R.string.error_fail_to_fetch_passbook_list)
            },
        )
    }
}