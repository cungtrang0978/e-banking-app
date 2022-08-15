package com.example.e_banking_app.data.api

import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.card.Card
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CardApi {

    @POST("cards")
    fun createCard(@Body requestBody: RequestBody): retrofit2.Call<BaseApiResponse<Any>>

    @GET("cards/{token}")
    fun getCardList(
        @Path("token") token: String,
    ): retrofit2.Call<BaseApiResponse<List<Card>>>
}