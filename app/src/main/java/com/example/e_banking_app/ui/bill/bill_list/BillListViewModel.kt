package com.example.e_banking_app.ui.bill.bill_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.repository.TransactionRepository

class BillListViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {

    private val _billListResult = MutableLiveData<BillListResult>()
    val billListResult: LiveData<BillListResult> = _billListResult

    init {
        fetchBillList()
    }

    fun fetchBillList() {
        transactionRepository.getBillUnpaid(
            onSuccess = {
                _billListResult.value = BillListResult(success = it)
            },
            onFailure = {
                _billListResult.value = BillListResult(error = R.string.fail_to_fetch_bill_list)

            }
        )
    }
}