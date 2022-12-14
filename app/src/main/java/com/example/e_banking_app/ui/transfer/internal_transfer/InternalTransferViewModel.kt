package com.example.e_banking_app.ui.transfer.internal_transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.input.CheckAccountInput
import com.example.e_banking_app.data.model.input.InternalTransferInput
import com.example.e_banking_app.data.repository.TransactionRepository

class InternalTransferViewModel(private val transactionRepository: TransactionRepository) :
    ViewModel() {
    private val _internalTransferFormState = MutableLiveData<InternalTransferFormState>()
    val internalTransferFormState: LiveData<InternalTransferFormState> =
        _internalTransferFormState

    private val _internalAccountState = MutableLiveData<InternalAccountState>()
    val internalAccountState: LiveData<InternalAccountState> =
        _internalAccountState

    private val _internalTransferResult = MutableLiveData<InternalTransferResult>()
    val internalTransferResult: LiveData<InternalTransferResult> = _internalTransferResult

    fun confirm(
        accountNumber: String,
        amount: String,
        message: String,
    ) {
        transactionRepository.createInternalTransfer(
            InternalTransferInput(
                to = accountNumber,
                money = amount,
                message = message,
            ),
            onSuccess = {
                _internalTransferResult.value =
                    InternalTransferResult(success = it)

            },
            onFailure = {
                _internalTransferResult.value =
                    InternalTransferResult(error = R.string.transfer_failure)

            },
        )
    }

    fun accountChanged(
        accountNumber: String,
    ) {
        transactionRepository.checkAccount(
            CheckAccountInput(account_number = accountNumber),
            onSuccess = {
                _internalAccountState.value = InternalAccountState(name = it)
            },
            onFailure = {
                _internalAccountState.value =
                    InternalAccountState(error = R.string.invalid_account_number)
            }
        )
    }

    fun dataChanged(
        accountNumber: String,
        amount: String,
        message: String,
        accountName: String,
    ) {
        val isValid = accountNumber.isNotBlank()
                && amount.isNotBlank() && message.isNotBlank()
                && accountName.isNotBlank()
        _internalTransferFormState.value =
            InternalTransferFormState(
                accountNumberError = if (accountNumber.isBlank() || accountName.isBlank())
                    R.string.invalid_account_number else null,
                amountError = if (amount.isBlank())
                    R.string.invalid_amount else null,
                messageError = if (message.isBlank())
                    R.string.invalid_message else null,
                isValid = isValid
            )
    }
}