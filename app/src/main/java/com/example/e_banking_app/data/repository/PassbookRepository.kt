package com.example.e_banking_app.data.repository

import android.content.Context
import android.util.Log
import com.example.e_banking_app.data.api.PassbookApi
import com.example.e_banking_app.data.api.ServiceBuilder
import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.passbook.Passbook
import com.example.e_banking_app.data.model.passbook.PassbookCategory
import com.example.e_banking_app.utils.AuthUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PassbookRepository(private val context: Context) {
    private val request = ServiceBuilder.buildService(PassbookApi::class.java)

    fun getPassbookCategoryList(
        onSuccess: (List<PassbookCategory>) -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            request.getPassbookCategoryList().enqueue(
                object : Callback<BaseApiResponse<List<PassbookCategory>>> {
                    override fun onResponse(
                        call: Call<BaseApiResponse<List<PassbookCategory>>>,
                        response: Response<BaseApiResponse<List<PassbookCategory>>>
                    ) {
                        if (response.isSuccessful && response.body()?.query_err == false && response.body()?.result != null) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: Call<BaseApiResponse<List<PassbookCategory>>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }

                },
            )

        } catch (e: Throwable) {
            Log.d("getPassbookCategoryList: ", e.toString())
            onFailure()
        }
    }

    fun getPassbookList(
        onSuccess: (List<Passbook>) -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            request.getPassbookList(AuthUtils.getToken(context)).enqueue(
                object : Callback<BaseApiResponse<List<Passbook>>> {
                    override fun onResponse(
                        call: Call<BaseApiResponse<List<Passbook>>>,
                        response: Response<BaseApiResponse<List<Passbook>>>
                    ) {
                        if (response.isSuccessful && response.body()?.query_err == false && response.body()?.result != null) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(
                        call: Call<BaseApiResponse<List<Passbook>>>,
                        t: Throwable
                    ) {
                        onFailure()
                    }

                },
            )

        } catch (e: Throwable) {
            Log.d("getPassbookCategoryList: ", e.toString())
            onFailure()
        }
    }
}