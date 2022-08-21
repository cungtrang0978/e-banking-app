package com.example.e_banking_app.data.api

import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.user.User
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("customers")
    fun changePassword(@Body requestBody: RequestBody): retrofit2.Call<BaseApiResponse<Any>>

    @GET("customers/{token}")
    fun getProfile(@Path("token") token: String): retrofit2.Call<BaseApiResponse<User>>
}