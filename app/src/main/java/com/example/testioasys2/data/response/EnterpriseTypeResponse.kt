package com.example.testioasys2.data.response

import com.google.gson.annotations.SerializedName

data class EnterpriseTypeResponse(
    @SerializedName("enterprise_type_name")
    val typeName:String
)
