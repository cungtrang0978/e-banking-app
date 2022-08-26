package com.example.e_banking_app.ui.transfer.interbank_transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.bank.Bank
import com.example.e_banking_app.data.model.input.CheckAccountInput
import com.example.e_banking_app.data.model.input.InterbankTransferInput
import com.example.e_banking_app.data.repository.TransactionRepository

class InterbankTransferViewModel(private val transactionRepository: TransactionRepository) :
    ViewModel() {
    private val _interbankTransferFormState = MutableLiveData<InterbankTransferFormState>()
    val interbankTransferFormState: LiveData<InterbankTransferFormState> =
        _interbankTransferFormState

    private val _interbankAccountState = MutableLiveData<InterbankAccountState>()
    val interbankAccountState: LiveData<InterbankAccountState> =
        _interbankAccountState

    private val _bankList = MutableLiveData<List<Bank>>()
    val bankList: LiveData<List<Bank>> = _bankList

    private val _interbankTransferResult = MutableLiveData<InterbankTransferResult>()
    val interbankTransferResult: LiveData<InterbankTransferResult> = _interbankTransferResult

    init {
        fetchBankList()
    }


    fun confirm(
        accountNumber: String,
        amount: String,
        message: String,
        selectedBank: Bank,
    ) {
        transactionRepository.createInterbankTransfer(
            InterbankTransferInput(
                account_number = accountNumber,
                money = amount,
                message = message,
                id_bank = selectedBank.id_bank
            ),
            onSuccess = {
                _interbankTransferResult.value =
                    InterbankTransferResult(success = R.string.transfer_successfully)

            },
            onFailure = {
                _interbankTransferResult.value =
                    InterbankTransferResult(error = R.string.transfer_failure)

            },
        )
    }

    fun accountChanged(
        accountNumber: String,
        selectedBank: Bank,
    ) {
        transactionRepository.checkAccount(
            CheckAccountInput(account_number = accountNumber, id_bank = selectedBank.id_bank),
            onSuccess = {
                _interbankAccountState.value = InterbankAccountState(name = it)
            },
            onFailure = {
                _interbankAccountState.value =
                    InterbankAccountState(error = R.string.invalid_account_number)
            }
        )
    }

    fun dataChanged(
        accountNumber: String,
        amount: String,
        message: String,
    ) {
        val isValid = accountNumber.isNotBlank() && amount.isNotBlank() && message.isNotBlank()
        _interbankTransferFormState.value =
            InterbankTransferFormState(
                accountNumberError = if (accountNumber.isBlank())
                    R.string.invalid_account_number else null,
                amountError = if (amount.isBlank())
                    R.string.invalid_amount else null,
                messageError = if (message.isBlank())
                    R.string.invalid_message else null,
                isValid = isValid
            )
    }

    private fun fetchBankList(
    ) {
        transactionRepository.getAllBankList(
            onSuccess = {
                _bankList.value = it
            },
            onFailure = {
                _bankList.value = emptyList()
            }
        )
    }
}