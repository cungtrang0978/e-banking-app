package com.example.e_banking_app.data.repository

import android.content.Context
import android.util.Log
import com.example.e_banking_app.data.api.ServiceBuilder
import com.example.e_banking_app.data.api.TransactionApi
import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.balance.BalanceItem
import com.example.e_banking_app.data.model.bank.Bank
import com.example.e_banking_app.data.model.bill.BillItem
import com.example.e_banking_app.data.model.input.*
import com.example.e_banking_app.data.model.transaction.Transaction
import com.example.e_banking_app.utils.AuthUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Callback
import retrofit2.Response

class TransactionRepository(private val context: Context) {
    private val request = ServiceBuilder.buildService(TransactionApi::class.java)

    fun createInternalTransfer(
        internalTransferInput: InternalTransferInput,
        onSuccess: (Transaction) -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            val body = internalTransferInput.copy(token = AuthUtils.getToken(context)).toJSON()
                .toRequestBody("application/json".toMediaTypeOrNull())
            request.createInternalTransfer(body).enqueue(
                object : Callback<BaseApiResponse<Transaction>> {
                    override fun onResponse(
                        call: retrofit2.Call<BaseApiResponse<Transaction>>,
                        response: Response<BaseApiResponse<Transaction>>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()?.query_err == false) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<BaseApiResponse<Transaction>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }

                }
            )
        } catch (e: Throwable) {
            Log.d("createInternalTransfer: ", e.toString())
            onFailure()
        }

    }


    fun checkAccount(
        checkAccountInput: CheckAccountInput,
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            val body = checkAccountInput.copy().toJSON()
                .toRequestBody("application/json".toMediaTypeOrNull())
            request.checkAccount(body).enqueue(
                object : Callback<BaseApiResponse<String>> {
                    override fun onResponse(
                        call: retrofit2.Call<BaseApiResponse<String>>,
                        response: Response<BaseApiResponse<String>>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()?.query_err == false) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<BaseApiResponse<String>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }

                }
            )
        } catch (e: Throwable) {
            Log.d("checkAccount: ", e.toString())
            onFailure()
        }

    }

    fun createInterbankTransfer(
        interbankTransferInput: InterbankTransferInput,
        onSuccess: (Transaction) -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            val body = interbankTransferInput.copy(token = AuthUtils.getToken(context)).toJSON()
                .toRequestBody("application/json".toMediaTypeOrNull())
            request.createInterbankTransfer(body).enqueue(
                object : Callback<BaseApiResponse<Transaction>> {
                    override fun onResponse(
                        call: retrofit2.Call<BaseApiResponse<Transaction>>,
                        response: Response<BaseApiResponse<Transaction>>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()?.query_err == false) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<BaseApiResponse<Transaction>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }

                }
            )
        } catch (e: Throwable) {
            Log.d("createInterbankTransfer: ", e.toString())
            onFailure()
        }

    }

    fun pay(
        billPayInput: BillPayInput,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            val body = billPayInput.copy(token = AuthUtils.getToken(context)).toJSON()
                .toRequestBody("application/json".toMediaTypeOrNull())
            request.pay(body).enqueue(
                object : Callback<BaseApiResponse<Any>> {
                    override fun onResponse(
                        call: retrofit2.Call<BaseApiResponse<Any>>,
                        response: Response<BaseApiResponse<Any>>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()?.query_err == false) {
                            onSuccess()
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<BaseApiResponse<Any>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }

                }
            )
        } catch (e: Throwable) {
            Log.d("pay: ", e.toString())
            onFailure()
        }

    }

    fun getAllBankList(
        onSuccess: (List<Bank>) -> Unit,
        onFailure: () -> Unit
    ) {
        try {

            request.getAllBankList().enqueue(
                object : Callback<BaseApiResponse<List<Bank>>> {
                    override fun onResponse(
                        call: retrofit2.Call<BaseApiResponse<List<Bank>>>,
                        response: Response<BaseApiResponse<List<Bank>>>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()?.query_err == false) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<BaseApiResponse<List<Bank>>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }

                }
            )
        } catch (e: Throwable) {
            Log.d("getAllBankList: ", e.toString())
            onFailure()
        }
    }

    fun getBalanceList(
        onSuccess: (List<BalanceItem>) -> Unit,
        onFailure: () -> Unit
    ) {
        try {

            request.getBalanceList(AuthUtils.getToken(context)).enqueue(
                object : Callback<BaseApiResponse<List<BalanceItem>>> {
                    override fun onResponse(
                        call: retrofit2.Call<BaseApiResponse<List<BalanceItem>>>,
                        response: Response<BaseApiResponse<List<BalanceItem>>>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()?.query_err == false) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<BaseApiResponse<List<BalanceItem>>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }

                }
            )
        } catch (e: Throwable) {
            Log.d("getBalanceList: ", e.toString())
            onFailure()
        }
    }

    fun getBillUnpaid(
        onSuccess: (List<BillItem>) -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            val body = BillListInput(token = AuthUtils.getToken(context)).toJSON()
                .toRequestBody("application/json".toMediaTypeOrNull())
            request.getBillUnpaid(body).enqueue(
                object : Callback<BaseApiResponse<List<BillItem>>> {
                    override fun onResponse(
                        call: retrofit2.Call<BaseApiResponse<List<BillItem>>>,
                        response: Response<BaseApiResponse<List<BillItem>>>
                    ) {
                        if (response.isSuccessful && response.body() != null && response.body()?.query_err == false) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: retrofit2.Call<BaseApiResponse<List<BillItem>>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }

                }
            )
        } catch (e: Throwable) {
            Log.d("getBillUnpaid: ", e.toString())
            onFailure()
        }

    }
}