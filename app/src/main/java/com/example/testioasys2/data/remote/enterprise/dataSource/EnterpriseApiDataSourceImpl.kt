package com.example.testioasys2.data.remote.enterprise.dataSource

import com.example.testioasys2.data.remote.enterprise.mapper.toDomain
import com.example.testioasys2.data.remote.rest.EnterpriseService
import com.example.testioasys2.data.remote.rest.retrofitWrapper
import com.example.testioasys2.domain.enterprise.Enterprise
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.domain.result.Result.Error
import com.example.testioasys2.domain.result.Result.Success

class EnterpriseApiDataSourceImpl(private val service: EnterpriseService): EnterpriseApiDataSource {
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
            is Success -> Success(result.data.enterprises.toDomain())
            is Error -> result
        }

    }
}