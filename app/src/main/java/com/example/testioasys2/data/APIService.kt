package com.example.testioasys2.data

import com.example.testioasys2.data.rest.EnterpriseService
import com.example.testioasys2.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {

    private fun initRetrofit(): Retrofit{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder().baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val service: EnterpriseService = initRetrofit().create(EnterpriseService::class.java)
}