package com.example.testioasys2.data

import okhttp3.Headers

sealed class LoginStatus{
    class Success(val userAccess: Headers): LoginStatus()
    class ApiError(val statusCode: Int): LoginStatus()
    object ServerError: LoginStatus()
}
