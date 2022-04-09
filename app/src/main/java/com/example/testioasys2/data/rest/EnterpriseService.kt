package com.example.testioasys2.data.rest

import com.example.testioasys2.data.UserSession
import com.example.testioasys2.data.model.UserRequest
import com.example.testioasys2.data.response.EnterpriseResultResponse
import com.example.testioasys2.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface EnterpriseService {

    @POST("users/auth/sign_in")
    fun dologin(@Body userRequest: UserRequest): Call<ResponseBody>

    @GET("enterprises")
    fun getEnterprises(
        @Header(Constants.ACCESS_TOKEN) accessToken: String = UserSession.getUserLoginCredentials(Constants.ACCESS_TOKEN),
        @Header(Constants.CLIENT) client: String = UserSession.getUserLoginCredentials(Constants.CLIENT),
        @Header(Constants.UID) uid: String = UserSession.getUserLoginCredentials(Constants.UID),
        @Query(Constants.NAME) name: String?
    ): Call<EnterpriseResultResponse>
}