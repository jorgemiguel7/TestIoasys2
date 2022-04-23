package com.example.testioasys2.data.repository

import com.example.testioasys2.data.UserSession
import com.example.testioasys2.data.dataSource.remote.model.EnterpriseApiDataSource
import com.example.testioasys2.data.model.Enterprise
import com.example.testioasys2.data.model.Result

class EnterpriseRepositoryImpl(private val apiDataSource: EnterpriseApiDataSource): EnterpriseRepository {
    override suspend fun getEnterprises(name: String?, userSession: UserSession): Result<List<Enterprise>> {
        return apiDataSource.getEnterprises(name, userSession)
    }
}