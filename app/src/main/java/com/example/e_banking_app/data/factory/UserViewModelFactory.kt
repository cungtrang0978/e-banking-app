package com.example.e_banking_app.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.data.data_source.UserDataSource
import com.example.e_banking_app.data.repository.UserRepository
import com.example.e_banking_app.ui.change_password.ChangePasswordViewModel
import com.example.e_banking_app.ui.profile.ProfileViewModel

class UserViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private var userRepository: UserRepository

    init {
        userRepository = UserRepository(dataSource = UserDataSource(context))
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangePasswordViewModel::class.java)) {
            return ChangePasswordViewModel(
                repository = userRepository
            ) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(
                userRepository = userRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
