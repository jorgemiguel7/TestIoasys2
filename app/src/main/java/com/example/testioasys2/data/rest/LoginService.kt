package com.example.testioasys2.data.rest

import com.example.testioasys2.data.remote.login.model.UserRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("users/auth/sign_in")
    suspend fun doLogin(@Body userRequest: UserRequest): Response<ResponseBody>
}