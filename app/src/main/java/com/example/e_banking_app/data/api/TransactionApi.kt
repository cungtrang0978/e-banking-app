package com.example.e_banking_app.data.api

import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.bank.Bank
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionApi {
    @POST("Transactions/InternalTranfer")
    fun createInternalTransfer(
        @Body requestBody: RequestBody,
    ): retrofit2.Call<BaseApiResponse<Any>>

    @POST("Transactions/CheckCustomer")
    fun checkAccount(
        @Body requestBody: RequestBody,
    ): retrofit2.Call<BaseApiResponse<String>>


    @POST("Transactions/ExternalTranfer")
    fun createInterbankTransfer(
        @Body requestBody: RequestBody,
    ): retrofit2.Call<BaseApiResponse<Any>>

    @GET("banks")
    fun getAllBankList(
    ): retrofit2.Call<BaseApiResponse<List<Bank>>>

    @POST("Bills")
    fun getBillUnpaid(
        @Body requestBody: RequestBody,
    ): retrofit2.Call<BaseApiResponse<Any>>
}