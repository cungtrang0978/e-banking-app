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
    private const val domain = "192.168.12.3";
//    private const val domain = "10.11.252.43";
//    private const val domain = "localhost";
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://${domain}:85/api-ebanking/api/v1/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}