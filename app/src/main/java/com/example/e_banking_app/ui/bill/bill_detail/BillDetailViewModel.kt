package com.example.e_banking_app.ui.bill.bill_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.bill.BillItem
import com.example.e_banking_app.data.model.input.BillPayInput
import com.example.e_banking_app.data.repository.TransactionRepository

class BillDetailViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {
    private val _billDetailResult = MutableLiveData<BillDetailResult>()
    val billDetailResult: LiveData<BillDetailResult> = _billDetailResult


    fun pay(billItem: BillItem) {
        transactionRepository.pay(
            BillPayInput(billItem.id_bill),
            onSuccess = {
                _billDetailResult.value = BillDetailResult(success = R.string.pay_successfully)
            },
            onFailure = {
                _billDetailResult.value = BillDetailResult(success = R.string.pay_fail)

            }
        )
    }


}