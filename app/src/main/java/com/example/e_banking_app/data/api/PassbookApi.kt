package com.example.e_banking_app.data.api

import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.passbook.Passbook
import com.example.e_banking_app.data.model.passbook.PassbookCategory
import retrofit2.http.GET
import retrofit2.http.Path

interface PassbookApi {
    @GET("CategoryPassbooks")
    fun getPassbookCategoryList():
            retrofit2.Call<BaseApiResponse<List<PassbookCategory>>>

    @GET("passbooks/{token}")
    fun getPassbookList(@Path("token") token: String):
            retrofit2.Call<BaseApiResponse<List<Passbook>>>
}