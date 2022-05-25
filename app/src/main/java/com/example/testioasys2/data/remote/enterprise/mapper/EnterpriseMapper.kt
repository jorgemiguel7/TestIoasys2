package com.example.testioasys2.data.remote.enterprise.mapper

import com.example.testioasys2.data.remote.enterprise.model.EnterpriseResponse
import com.example.testioasys2.domain.enterprise.Enterprise

fun List<EnterpriseResponse>?.toDomain(): List<Enterprise>{
    return this?.map { enterpriseResponse ->
        Enterprise(
            enterpriseResponse.name.orEmpty(),
            enterpriseResponse.photo.orEmpty(),
            enterpriseResponse.type?.typeName.orEmpty(),
            enterpriseResponse.city.orEmpty(),
            enterpriseResponse.description.orEmpty()
        )
    } ?: listOf()
}