package com.example.e_banking_app.data.data_source

import android.util.Log
import com.example.e_banking_app.R
import com.example.e_banking_app.data.api.AuthApi
import com.example.e_banking_app.data.api.ServiceBuilder
import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.input.ForgotPasswordInput
import com.example.e_banking_app.data.model.input.LoginInput
import com.example.e_banking_app.data.model.input.RegisterInput
import com.example.e_banking_app.intefaces.JSONConvertible
import com.example.e_banking_app.ui.login.LoginResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthDataSource {
    private val request = ServiceBuilder.buildService(AuthApi::class.java)

    fun login(
        loginInput: LoginInput,
        onSuccess: (LoginResponse) -> Unit,
        onFailure: () -> Unit,
    ) {

        try {
            val requestBody =
                loginInput.toJSON().toRequestBody("application/json".toMediaTypeOrNull())
            request.login(requestBody).enqueue(
                object : Callback<BaseApiResponse<String>> {
                    override fun onResponse(
                        call: Call<BaseApiResponse<String>>,
                        response: Response<BaseApiResponse<String>>
                    ) {
                        if (response.isSuccessful && response.body()?.query_err == false) {
                            //TODO: handle later
                            Log.d("TOKEN", response.body().toString())
                            onSuccess(LoginResponse(response.body()!!.result))
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: Call<BaseApiResponse<String>>,
                        t: Throwable
                    ) {
                        onFailure()
//                        onSuccess(LoginResponse("x5z7TnsYpq6KFzhHXzwbQEvQt"))

                    }
                },
            )

        } catch (e: Throwable) {
            Log.d("login: ", e.toString())
            onFailure()
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    fun register(
        registerInput: RegisterInput,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            val requestBody =
                registerInput.toJSON().toRequestBody("application/json".toMediaTypeOrNull())
            request.register(requestBody).enqueue(
                object : Callback<BaseApiResponse<Any>> {
                    override fun onResponse(
                        call: Call<BaseApiResponse<Any>>,
                        response: Response<BaseApiResponse<Any>>
                    ) {
                        if (response.isSuccessful && response.body()?.query_err == false) {
                                onSuccess()
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(call: Call<BaseApiResponse<Any>>, t: Throwable) {
                        onFailure()
                    }

                },
            )
        } catch (e: Throwable) {
            Log.d("register: ", e.toString())
            onFailure()
        }
    }

    fun checkPhoneNumber(
        phoneNumber: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            val requestBody =
                PhoneNumberInput(phoneNumber).toJSON()
                    .toRequestBody("application/json".toMediaTypeOrNull())
            request.checkPhoneNumber(requestBody).enqueue(
                object : Callback<BaseApiResponse<Boolean>> {
                    override fun onResponse(
                        call: Call<BaseApiResponse<Boolean>>,
                        response: Response<BaseApiResponse<Boolean>>
                    ) {
                        if (response.isSuccessful && response.body()?.query_err == false && response.body() != null) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                            R.string.bill_amount
                        }
                    }

                    override fun onFailure(call: Call<BaseApiResponse<Boolean>>, t: Throwable) {
                        onFailure()
                    }

                },
            )
        } catch (e: Throwable) {
            Log.d("checkPhoneNumber: ", e.toString())
            onFailure()
        }
    }

    fun sendForgotPasswordMail(
        forgotPasswordInput: ForgotPasswordInput,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            val requestBody =
                forgotPasswordInput.toJSON()
                    .toRequestBody("application/json".toMediaTypeOrNull())
            request.sendForgotPasswordMail(requestBody).enqueue(
                object : Callback<BaseApiResponse<Any>> {
                    override fun onResponse(
                        call: Call<BaseApiResponse<Any>>,
                        response: Response<BaseApiResponse<Any>>
                    ) {
                        if (response.isSuccessful && response.body()?.query_err == false) {
                            onSuccess()
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(call: Call<BaseApiResponse<Any>>, t: Throwable) {
                        onFailure()
                    }

                },
            )
        } catch (e: Throwable) {
            Log.d("sendForgotPasswordMail: ", e.toString())
            onFailure()
        }
    }

    inner class PhoneNumberInput(val phone: String) : JSONConvertible
}