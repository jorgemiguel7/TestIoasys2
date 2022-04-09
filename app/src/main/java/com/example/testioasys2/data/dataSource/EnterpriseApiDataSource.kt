package com.example.testioasys2.data.dataSource

import com.example.testioasys2.data.APIService
import com.example.testioasys2.data.EnterpriseStatus
import com.example.testioasys2.data.model.Enterprise
import com.example.testioasys2.data.repository.EnterpriseRepository
import com.example.testioasys2.data.response.EnterpriseResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterpriseApiDataSource: EnterpriseRepository {
    override fun getEnterprises(name: String?, enterpriseStatusCallBack: (enterpriseStatus: EnterpriseStatus) -> Unit) {
        APIService.service.getEnterprises(name = name).enqueue(object : Callback<EnterpriseResultResponse>{
            override fun onResponse(call: Call<EnterpriseResultResponse>, response: Response<EnterpriseResultResponse>) {
                when{
                    response.isSuccessful ->{
                        val enterprises: MutableList<Enterprise> = mutableListOf()

                        response.body()?.let { enterpriseResponse ->
                            for (result in enterpriseResponse.enterprises){
                                val enterprise = Enterprise(
                                    result.name,
                                    result.photo,
                                    result.type.typeName,
                                    result.city,
                                    result.description
                                )
                                enterprises.add(enterprise)
                            }
                        }

                        enterpriseStatusCallBack(EnterpriseStatus.Success(enterprises))
                    }
                    else ->{
                        enterpriseStatusCallBack(EnterpriseStatus.ApiError(response.code()))
                    }
                }
            }

            override fun onFailure(call: Call<EnterpriseResultResponse>, t: Throwable) {
                enterpriseStatusCallBack(EnterpriseStatus.ServerError)
            }
        })
    }
}