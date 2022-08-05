package com.example.e_banking_app.data.api

import com.example.e_banking_app.data.model.BaseApiResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("customers/signup")
    fun register(@Body requestBody: RequestBody): retrofit2.Call<BaseApiResponse<Any>>

    @POST("signIn")
    fun login(@Body requestBody: RequestBody): retrofit2.Call<BaseApiResponse<String>>
}