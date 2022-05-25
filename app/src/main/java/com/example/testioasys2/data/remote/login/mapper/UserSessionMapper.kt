package com.example.testioasys2.data.remote.login.mapper

import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.utils.Constants
import retrofit2.Response

fun <T> Response<T>.toUserSession(): UserSession{
    return UserSession(
        accessToken = headers()[Constants.ACCESS_TOKEN].orEmpty(),
        client = headers()[Constants.CLIENT].orEmpty(),
        uid = headers()[Constants.UID].orEmpty()
    )
}