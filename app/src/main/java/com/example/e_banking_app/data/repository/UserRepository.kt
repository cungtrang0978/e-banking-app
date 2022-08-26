package com.example.e_banking_app.data.repository

import com.example.e_banking_app.data.data_source.UserDataSource
import com.example.e_banking_app.data.model.input.ChangePasswordInput
import com.example.e_banking_app.data.model.user.User

class UserRepository(private val dataSource: UserDataSource) {
    fun changePassword(
        changePasswordInput: ChangePasswordInput,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        dataSource.changePassword(changePasswordInput, onSuccess, onFailure)
    }

    fun getProfile(
        onSuccess: (User) -> Unit,
        onFailure: () -> Unit,
    ) {
        dataSource.getProfile(onSuccess, onFailure)
    }
}