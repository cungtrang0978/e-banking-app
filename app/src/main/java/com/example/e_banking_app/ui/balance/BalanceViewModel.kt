package com.example.e_banking_app.ui.balance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.repository.TransactionRepository

class BalanceViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {
    private val _balanceResult = MutableLiveData<BalanceResult>()
    val balanceResult: LiveData<BalanceResult> = _balanceResult

    init {
        fetchBalanceList()
    }

    private fun fetchBalanceList() {
        transactionRepository.getBalanceList(
            onSuccess = {
                _balanceResult.value = BalanceResult(success = it)
            },
            onFailure = {
                _balanceResult.value = BalanceResult(error = R.string.fail_to_fetch_balance)
            }
        )
    }
}