package com.example.e_banking_app.data.data_source

import android.util.Log
import com.example.e_banking_app.data.api.AuthApi
import com.example.e_banking_app.data.api.ServiceBuilder
import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.input.LoginInput
import com.example.e_banking_app.data.model.input.RegisterInput
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
                object : Callback<BaseApiResponse<LoginResponse>> {
                    override fun onResponse(
                        call: Call<BaseApiResponse<LoginResponse>>,
                        response: Response<BaseApiResponse<LoginResponse>>
                    ) {
                        if (response.isSuccessful && response.body()?.query_err == false) {
                            //TODO: handle later
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: Call<BaseApiResponse<LoginResponse>>,
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
}