package com.example.e_banking_app.data.repository

import com.example.e_banking_app.data.data_source.MasterDataSource
import com.example.e_banking_app.data.model.Branch

class MasterRepository(private val dataSource: MasterDataSource) {

    fun fetchBranchList(
        onSuccess: (List<Branch>) -> Unit,
        onFailure: () -> Unit,
    ) {
        dataSource.fetchBranchList(onSuccess, onFailure)
    }
}