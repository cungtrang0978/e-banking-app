package com.example.e_banking_app.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.data.repository.TransactionRepository
import com.example.e_banking_app.ui.transfer.interbank_transfer.InterbankTransferViewModel
import com.example.e_banking_app.ui.transfer.internal_transfer.InternalTransferViewModel

class TransactionViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private var transactionRepository: TransactionRepository

    init {
        transactionRepository = TransactionRepository(context)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InternalTransferViewModel::class.java)) {
            return InternalTransferViewModel(
                transactionRepository
            ) as T
        } else if (modelClass.isAssignableFrom(InterbankTransferViewModel::class.java)) {
            return InterbankTransferViewModel(
                transactionRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}