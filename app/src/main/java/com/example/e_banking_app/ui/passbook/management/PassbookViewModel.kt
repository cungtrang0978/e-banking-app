package com.example.e_banking_app.ui.passbook.management

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.input.WithdrawPassbookInput
import com.example.e_banking_app.data.model.passbook.Passbook
import com.example.e_banking_app.data.repository.PassbookRepository

class PassbookViewModel(private val passbookRepository: PassbookRepository) : ViewModel() {
    private val _passbookResult = MutableLiveData<PassbookResult>()
    val passbookResult: LiveData<PassbookResult> = _passbookResult

    private val _withdrawResult = MutableLiveData<WithdrawResult>()
    val withdrawResult: LiveData<WithdrawResult> = _withdrawResult


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

    fun withdrawPassbook(passbook: Passbook) {
        passbookRepository.withdraw(
            WithdrawPassbookInput(id_passbook = passbook.id_passbook),
            onSuccess = {
                _withdrawResult.value = WithdrawResult(success = R.string.withdraw_successfully)
            },
            onFailure = {
                _withdrawResult.value = WithdrawResult(error = R.string.fail_to_withdraw)

            }
        )
    }
}