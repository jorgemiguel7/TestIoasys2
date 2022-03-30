package com.example.testioasys2.data.repository

import com.example.testioasys2.data.APIService
import com.example.testioasys2.data.EnterpriseStatus
import com.example.testioasys2.data.response.EnterpriseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterpriseApiDataSource: EnterpriseRepository {
    override fun getEnterprises(name: String?, enterpriseStatusCallBack: (enterpriseStatus: EnterpriseStatus) -> Unit) {
        APIService.service.getEnterprises(name = name).enqueue(object : Callback<EnterpriseResponse>{
            override fun onResponse(call: Call<EnterpriseResponse>, response: Response<EnterpriseResponse>) {
                when{
                    response.isSuccessful ->{
                        if (response.body() != null){
                            enterpriseStatusCallBack(EnterpriseStatus.Success(
                                response.body()!!.enterprises))
                        }
                    }
                    else ->{
                        enterpriseStatusCallBack(EnterpriseStatus.ApiError(response.code()))
                    }
                }
            }

            override fun onFailure(call: Call<EnterpriseResponse>, t: Throwable) {
                enterpriseStatusCallBack(EnterpriseStatus.ServerError)
            }
        })
    }
}