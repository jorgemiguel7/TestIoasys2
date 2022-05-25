package com.example.testioasys2.domain.use_case

import com.example.testioasys2.domain.enterprise.Enterprise
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result

interface GetEnterpriseList {
    suspend fun call(name: String?, userSession: UserSession): Result<List<Enterprise>>
}