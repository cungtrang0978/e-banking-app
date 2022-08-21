package com.example.e_banking_app.data.data_source

import android.util.Log
import com.example.e_banking_app.data.api.ServiceBuilder
import com.example.e_banking_app.data.api.UserApi
import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.input.ChangePasswordInput
import com.example.e_banking_app.data.model.user.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDataSource {
    private val request = ServiceBuilder.buildService(UserApi::class.java)

    fun changePassword(
        changePasswordInput: ChangePasswordInput,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            val requestBody =
                changePasswordInput.toJSON().toRequestBody("application/json".toMediaTypeOrNull())
            request.changePassword(requestBody).enqueue(
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

                    override fun onFailure(
                        call: Call<BaseApiResponse<Any>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }
                },
            )

        } catch (e: Throwable) {
            Log.d("login: ", e.toString())
            onFailure()
        }
    }

    fun getProfile(
        token: String,
        onSuccess: (User) -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            request.getProfile(token).enqueue(
                object : Callback<BaseApiResponse<User>> {
                    override fun onResponse(
                        call: Call<BaseApiResponse<User>>,
                        response: Response<BaseApiResponse<User>>
                    ) {
                        if (response.isSuccessful && response.body()?.query_err == false) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: Call<BaseApiResponse<User>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }
                },
            )

        } catch (e: Throwable) {
            Log.d("login: ", e.toString())
            onFailure()
        }
    }
}