package com.example.testioasys2.data.remote.enterprise.mapper

import com.example.testioasys2.data.remote.enterprise.model.EnterpriseResponse
import com.example.testioasys2.data.remote.enterprise.model.EnterpriseTypeResponse
import com.example.testioasys2.domain.enterprise.Enterprise
import org.junit.Assert.assertEquals
import org.junit.Test

class EnterpriseMapperTest {

    @Test
    fun `WHEN map a null list THEN return empty list`() {
        val enterpriseResponseList: List<EnterpriseResponse>? = null
        val result = enterpriseResponseList.toDomain()

        val expected = emptyList<Enterprise>()
        assertEquals(expected, result)
    }

    @Test
    fun `WHEN map a enterprise response list AND has null attributes THEN return mapped list with empty attributes`() {
        val enterpriseResponseList = listOf(
            EnterpriseResponse(null, null, null, null, null, null),
        )
        val result = enterpriseResponseList.toDomain()

        val expected = listOf(
            Enterprise("", "", "", "", "")
        )
        assertEquals(expected, result)
    }

    @Test
    fun `WHEN map a enterprise response list THEN return mapped list`() {
        val id = "01"
        val name = "test"
        val photo = "http://teste.com/test"
        val description = "testando"
        val city = "test"
        val typeName = "test"
        val enterpriseTypeResponse = EnterpriseTypeResponse(typeName)

        val enterpriseResponseList = listOf(
            EnterpriseResponse(id, name, photo, description, city, enterpriseTypeResponse)
        )
        val result = enterpriseResponseList.toDomain()

        val expected = listOf(
            Enterprise(name, photo, typeName, city, description)
        )
        assertEquals(expected, result)
    }

}