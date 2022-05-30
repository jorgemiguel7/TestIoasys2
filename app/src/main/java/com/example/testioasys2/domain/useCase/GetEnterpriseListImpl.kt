package com.example.testioasys2.domain.useCase

import com.example.testioasys2.domain.enterprise.Enterprise
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.repository.enterprise.EnterpriseRepository
import com.example.testioasys2.domain.result.Result

class GetEnterpriseListImpl(private val enterpriseRepository: EnterpriseRepository): GetEnterpriseList {
    override suspend fun call(name: String?, userSession: UserSession): Result<List<Enterprise>> {
        return enterpriseRepository.getEnterprises(name, userSession)
    }
}