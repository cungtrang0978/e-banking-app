package com.example.e_banking_app.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()
    private var gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.12.5:85/api-ebanking/api/v1/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}