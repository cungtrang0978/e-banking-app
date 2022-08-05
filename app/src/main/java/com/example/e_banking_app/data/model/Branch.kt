package com.example.e_banking_app.data.model

import com.google.gson.annotations.SerializedName

data class Branch(
    @SerializedName("id_branch")
    val branchId: Int,
    @SerializedName("name_branch")
    val nameBranch: String,
    @SerializedName("location_branch")
    val locationBranch: String,
    val state: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String?,
)
