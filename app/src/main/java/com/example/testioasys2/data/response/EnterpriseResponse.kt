package com.example.testioasys2.data.response

import com.google.gson.annotations.SerializedName

data class EnterpriseResponse(
    val id: String,
    @SerializedName("enterprise_name")
    val name: String,
    val photo: String,
    val description: String,
    val city: String,
    @SerializedName("enterprise_type")
    val type: EnterpriseTypeResponse
)
