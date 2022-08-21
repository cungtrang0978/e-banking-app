package com.example.e_banking_app.data.data_source

import android.util.Log
import com.example.e_banking_app.data.api.CardApi
import com.example.e_banking_app.data.api.ServiceBuilder
import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.card.Card
import com.example.e_banking_app.data.model.input.CreateCardInput
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardDataSource {
    private val request = ServiceBuilder.buildService(CardApi::class.java)

    fun createCard(
        createCardInput: CreateCardInput,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            val requestBody =
                createCardInput.toJSON().toRequestBody("application/json".toMediaTypeOrNull())
            request.createCard(requestBody).enqueue(
                object : Callback<BaseApiResponse<Any>> {
                    override fun onResponse(
                        call: Call<BaseApiResponse<Any>>,
                        response: Response<BaseApiResponse<Any>>
                    ) {
                        if (response.isSuccessful && response.body()?.query_err == false) {
                            onSuccess()
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(call: Call<BaseApiResponse<Any>>, t: Throwable) {
                        onFailure()
                    }

                },
            )
        } catch (e: Throwable) {
            Log.d("createCard: ", e.toString())
            onFailure()
        }
    }

    fun getCardList(
        token: String,
        onSuccess: (List<Card>) -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            request.getCardList(token).enqueue(
                object : Callback<BaseApiResponse<List<Card>>> {
                    override fun onResponse(
                        call: Call<BaseApiResponse<List<Card>>>,
                        response: Response<BaseApiResponse<List<Card>>>
                    ) {
                        if (response.isSuccessful && response.body()?.query_err == false) {
                            onSuccess(response.body()!!.result)
                        } else {
                            onFailure()
                        }
                    }

                    override fun onFailure(call: Call<BaseApiResponse<List<Card>>>, t: Throwable) {
                        onFailure()
                    }

                }
            )
        } catch (e: Throwable) {
            Log.d("getCardList: ", e.toString())
            onFailure()
        }
    }
}