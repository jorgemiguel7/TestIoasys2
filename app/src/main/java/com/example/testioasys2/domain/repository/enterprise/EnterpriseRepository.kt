package com.example.testioasys2.domain.repository.enterprise

import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.enterprise.Enterprise
import com.example.testioasys2.domain.result.Result

interface EnterpriseRepository {
    suspend fun getEnterprises(name: String?, userSession: UserSession): Result<List<Enterprise>>
}