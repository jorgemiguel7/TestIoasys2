package com.example.testioasys2.data

import com.example.testioasys2.data.model.Enterprise

sealed class EnterpriseStatus {
    class Success(val enterprises: List<Enterprise>) : EnterpriseStatus()
    class ApiError(val statusCode: Int): EnterpriseStatus()
    object ServerError: EnterpriseStatus()
}