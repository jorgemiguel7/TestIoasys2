package com.example.testioasys2.data.dataSource.remote.model

import com.example.testioasys2.data.UserSession
import com.example.testioasys2.data.model.Enterprise
import com.example.testioasys2.data.model.Result

interface EnterpriseApiDataSource {
    suspend fun getEnterprises(name: String?, userSession: UserSession): Result<List<Enterprise>>
}