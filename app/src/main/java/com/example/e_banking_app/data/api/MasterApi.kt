package com.example.e_banking_app.data.api

import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.Branch
import retrofit2.http.GET

interface MasterApi {
    @GET("branches")
    fun getBranchList(): retrofit2.Call<BaseApiResponse<List<Branch>>>

}