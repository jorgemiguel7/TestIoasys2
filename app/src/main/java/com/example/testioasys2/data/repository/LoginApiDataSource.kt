package com.example.testioasys2.data.repository

import com.example.testioasys2.data.APIService
import com.example.testioasys2.data.LoginStatus
import com.example.testioasys2.data.model.UserRequest
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginApiDataSource: LoginRepository {
    override fun doLogin(userRequest: UserRequest, access: (loginStatus: LoginStatus) -> Unit) {
        APIService.service.login(userRequest).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when{
                    response.isSuccessful ->{
                        val headers: Headers = response.headers()

                        access.invoke(LoginStatus.Success(headers))
                    }
                    else ->{
                        access.invoke(LoginStatus.ApiError(response.code()))
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                access.invoke(LoginStatus.ServerError)
            }
        })
    }
}