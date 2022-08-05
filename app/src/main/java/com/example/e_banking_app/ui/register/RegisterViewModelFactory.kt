package com.example.e_banking_app.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.data.data_source.AuthDataSource
import com.example.e_banking_app.data.data_source.MasterDataSource
import com.example.e_banking_app.data.repository.AuthRepository
import com.example.e_banking_app.data.repository.MasterRepository

class RegisterViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                authRepository = AuthRepository(
                    dataSource = AuthDataSource()
                ),
                masterRepository = MasterRepository(
                    dataSource = MasterDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}