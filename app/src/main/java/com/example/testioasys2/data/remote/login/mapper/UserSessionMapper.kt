package com.example.testioasys2.data.remote.login.mapper

import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Response

fun Response<ResponseBody>.toUserSession(): UserSession{
    return UserSession(
        accessToken = headers()[Constants.ACCESS_TOKEN].orEmpty(),
        client = headers()[Constants.CLIENT].orEmpty(),
        uid = headers()[Constants.UID].orEmpty()
    )
}