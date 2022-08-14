package com.example.e_banking_app.data.api

import com.example.e_banking_app.data.model.BaseApiResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("user")
    fun changePassword(@Body requestBody: RequestBody): retrofit2.Call<BaseApiResponse<Any>>

}