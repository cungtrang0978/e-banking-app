package com.example.e_banking_app.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.data.data_source.UserDataSource
import com.example.e_banking_app.data.repository.UserRepository
import com.example.e_banking_app.ui.change_password.ChangePasswordViewModel
import com.example.e_banking_app.ui.check_balance.CheckBalanceViewModel
import com.example.e_banking_app.ui.info.InfoViewModel
import com.example.e_banking_app.ui.otp.OtpVerificationViewModel
import com.example.e_banking_app.ui.profile.ProfileViewModel

class UserViewModelFactory(context: Context) : ViewModelProvider.Factory {

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
        } else if (modelClass.isAssignableFrom(OtpVerificationViewModel::class.java)) {
            return OtpVerificationViewModel(
                userRepository = userRepository
            ) as T
        } else if (modelClass.isAssignableFrom(InfoViewModel::class.java)) {
            return InfoViewModel(
                userRepository = userRepository
            ) as T
        } else if (modelClass.isAssignableFrom(CheckBalanceViewModel::class.java)) {
            return CheckBalanceViewModel(
                userRepository = userRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

