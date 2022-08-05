package com.example.e_banking_app.data.data_source

import com.example.e_banking_app.data.api.MasterApi
import com.example.e_banking_app.data.api.ServiceBuilder
import com.example.e_banking_app.data.model.BaseApiResponse
import com.example.e_banking_app.data.model.Branch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MasterDataSource {
    private val request = ServiceBuilder.buildService(MasterApi::class.java)

    fun fetchBranchList(
        onSuccess: (List<Branch>) -> Unit,
        onFailure: () -> Unit,
    ) {
        request.getBranchList().enqueue(
            object : Callback<BaseApiResponse<List<Branch>>> {
                override fun onResponse(
                    call: Call<BaseApiResponse<List<Branch>>>,
                    response: Response<BaseApiResponse<List<Branch>>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { onSuccess(it.result) }
                    } else {
                        onFailure()
                    }
                }

                override fun onFailure(call: Call<BaseApiResponse<List<Branch>>>, t: Throwable) {
                    onFailure()
                }
            },
        )
    }
}