package com.example.testioasys2.data.repository

import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.data.remote.enterprise.dataSource.EnterpriseApiDataSource
import com.example.testioasys2.domain.enterprise.Enterprise
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.domain.repository.EnterpriseRepository

class EnterpriseRepositoryImpl(private val apiDataSource: EnterpriseApiDataSource):
    EnterpriseRepository {
    override suspend fun getEnterprises(name: String?, userSession: UserSession): Result<List<Enterprise>> {
        return apiDataSource.getEnterprises(name, userSession)
    }
}