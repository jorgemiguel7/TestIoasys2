package com.example.testioasys2.data

import com.example.testioasys2.data.response.EnterpriseDataResponse

sealed class EnterpriseStatus {
    class Success(val enterprises: List<EnterpriseDataResponse>) : EnterpriseStatus()
    class ApiError(val statusCode: Int): EnterpriseStatus()
    object ServerError: EnterpriseStatus()
}