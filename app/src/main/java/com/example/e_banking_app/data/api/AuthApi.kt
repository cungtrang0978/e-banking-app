package com.example.e_banking_app.data.api

import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.ui.login.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("customers/signup")
    fun register(@Body requestBody: RequestBody): retrofit2.Call<BaseApiResponse<Any>>

    @POST("customers/signin")
    fun login(@Body requestBody: RequestBody): retrofit2.Call<BaseApiResponse<String>>
}