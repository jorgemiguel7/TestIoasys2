package com.example.testioasys2.data.rest

import com.example.testioasys2.data.model.UserRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EnterpriseService {

    @POST("users/auth/sign_in")
    fun login(@Body userRequest: UserRequest): Call<ResponseBody>
}