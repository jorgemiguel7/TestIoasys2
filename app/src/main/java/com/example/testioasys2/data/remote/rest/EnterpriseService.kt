package com.example.testioasys2.data.remote.rest

import com.example.testioasys2.data.remote.enterprise.model.EnterpriseResultResponse
import com.example.testioasys2.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface EnterpriseService {

    @GET("enterprises")
    suspend fun getEnterprises(
        @Header(Constants.ACCESS_TOKEN) accessToken: String,
        @Header(Constants.CLIENT) client: String,
        @Header(Constants.UID) uid: String,
        @Query(Constants.NAME) name: String?
    ): EnterpriseResultResponse
}