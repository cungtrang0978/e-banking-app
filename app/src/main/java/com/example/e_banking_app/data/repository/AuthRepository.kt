package com.example.e_banking_app.data.repository

import com.example.e_banking_app.data.data_source.AuthDataSource
import com.example.e_banking_app.data.model.LoggedInUser
import com.example.e_banking_app.data.model.input.LoginInput
import com.example.e_banking_app.data.model.input.RegisterInput
import com.example.e_banking_app.ui.login.LoginResponse

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class AuthRepository(private val dataSource: AuthDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(
        loginInput: LoginInput,
        onSuccess: (LoginResponse) -> Unit,
        onFailure: () -> Unit,
    ) {
        // handle login
        dataSource.login(loginInput, onSuccess, onFailure)

//        if (result is Result.Success) {
//            setLoggedInUser(result.data)
//        }
//
//        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    fun register(
        registerInput: RegisterInput,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        dataSource.register(registerInput, onSuccess, onFailure)
    }

    fun checkPhoneNumber(
        phoneNumber: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: () -> Unit,
    ) {
        dataSource.checkPhoneNumber(phoneNumber, onSuccess, onFailure)
    }

    fun sendForgotPasswordMail(
        phoneNumber: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        dataSource.sendForgotPasswordMail(phoneNumber, onSuccess, onFailure)
    }
}