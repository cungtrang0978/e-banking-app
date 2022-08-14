package com.example.e_banking_app.data.repository

import com.example.e_banking_app.data.data_source.UserDataSource
import com.example.e_banking_app.data.model.input.ChangePasswordInput

class UserRepository(private  val dataSource: UserDataSource) {
    fun changePassword(
        changePasswordInput: ChangePasswordInput,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ){
        dataSource.changePassword(changePasswordInput, onSuccess, onFailure)
    }
}