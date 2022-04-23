package com.example.testioasys2.data.dataSource

import com.example.testioasys2.data.UserSession
import com.example.testioasys2.data.dataSource.remote.model.EnterpriseApiDataSource
import com.example.testioasys2.data.model.Enterprise
import com.example.testioasys2.data.model.Result
import com.example.testioasys2.data.model.Result.Error
import com.example.testioasys2.data.model.Result.Success
import com.example.testioasys2.data.response.EnterpriseResponse
import com.example.testioasys2.data.rest.EnterpriseService
import com.example.testioasys2.data.rest.retrofitWrapper

class EnterpriseApiDataSourceImpl(private val service: EnterpriseService): EnterpriseApiDataSource{
    override suspend fun getEnterprises(name: String?, userSession: UserSession): Result<List<Enterprise>> {
        val result = retrofitWrapper {
            service.getEnterprises(
                userSession.accessToken,
                userSession.client,
                userSession.uid,
                name
            )
        }

        return when(result){
            is Success -> Success(mapToEnterpriseList(result.data.enterprises))
            is Error -> result
        }

    }

    private fun mapToEnterpriseList(result: List<EnterpriseResponse>): List<Enterprise> {
        val enterprises: MutableList<Enterprise> = mutableListOf()
        for (inResult in result) {
            val enterprise = Enterprise(
                inResult.name,
                inResult.photo,
                inResult.type.typeName,
                inResult.city,
                inResult.description
            )
            enterprises.add(enterprise)
        }
        return enterprises
    }
}