package com.example.e_banking_app.data.api

import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.passbook.Passbook
import com.example.e_banking_app.data.model.passbook.PassbookCategory
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PassbookApi {
    @GET("CategoryPassbooks")
    fun getPassbookCategoryList():
            retrofit2.Call<BaseApiResponse<List<PassbookCategory>>>

    @GET("passbooks/GetPassbookByCustomer/{token}")
    fun getPassbookList(@Path("token") token: String):
            retrofit2.Call<BaseApiResponse<List<Passbook>>>

    @POST("passbooks")
    fun addPassbook(@Body requestBody: RequestBody ):
            retrofit2.Call<BaseApiResponse<Any>>

    @POST("passbooks/check")
    fun withdraw(
        @Body requestBody: RequestBody,
    ): retrofit2.Call<BaseApiResponse<Any>>

}