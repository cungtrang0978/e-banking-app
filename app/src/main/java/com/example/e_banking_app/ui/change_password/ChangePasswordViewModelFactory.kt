package com.example.e_banking_app.ui.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.data.data_source.UserDataSource
import com.example.e_banking_app.data.repository.UserRepository

class ChangePasswordViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangePasswordViewModel::class.java)) {
            return ChangePasswordViewModel(
                repository = UserRepository(dataSource = UserDataSource())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}