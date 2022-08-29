package com.example.e_banking_app.data.api

import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.bank.Bank
import com.example.e_banking_app.data.model.transaction.Transaction
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionApi {
    @POST("Transactions/InternalTranfer")
    fun createInternalTransfer(
        @Body requestBody: RequestBody,
    ): retrofit2.Call<BaseApiResponse<Transaction>>

    @POST("Transactions/CheckCustomer")
    fun checkAccount(
        @Body requestBody: RequestBody,
    ): retrofit2.Call<BaseApiResponse<String>>


    @POST("Transactions/ExternalTranfer")
    fun createInterbankTransfer(
        @Body requestBody: RequestBody,
    ): retrofit2.Call<BaseApiResponse<Transaction>>

    @GET("banks")
    fun getAllBankList(
    ): retrofit2.Call<BaseApiResponse<List<Bank>>>

    @GET("Transactions")
    fun getBalanceList(
    ): retrofit2.Call<BaseApiResponse<List<Transaction>>>

    @POST("Bills")
    fun getBillUnpaid(
        @Body requestBody: RequestBody,
    ): retrofit2.Call<BaseApiResponse<Any>>
}