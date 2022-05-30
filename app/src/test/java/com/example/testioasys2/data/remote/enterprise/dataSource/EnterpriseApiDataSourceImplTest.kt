package com.example.testioasys2.data.remote.enterprise.dataSource

import com.example.testioasys2.data.remote.enterprise.mapper.toDomain
import com.example.testioasys2.data.remote.enterprise.model.EnterpriseResultResponse
import com.example.testioasys2.data.remote.rest.EnterpriseService
import com.example.testioasys2.domain.exception.NetworkErrorException
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class EnterpriseApiDataSourceImplTest {

    private val service: EnterpriseService = mockk()
    private lateinit var enterpriseApiDataSourceImpl: EnterpriseApiDataSourceImpl

    @Before
    fun setupTest() {
        enterpriseApiDataSourceImpl = EnterpriseApiDataSourceImpl(service)
    }

    @After
    fun resetMocks() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN a call on getEnterprises THEN call service with correct params`() = runBlockingTest {
        val name = "test"
        val accessToken = "test"
        val client = "test"
        val uid = "test"
        val enterprise = "test"
        val userSession = UserSession(accessToken, client, uid)
        val enterpriseResultResponse = EnterpriseResultResponse(
            enterprises = listOf(mockk(relaxed = true), mockk(relaxed = true))
        )

        coEvery { service.getEnterprises(any(), any(), any(), any()) } returns enterpriseResultResponse

        enterpriseApiDataSourceImpl.getEnterprises(name, userSession)

        coVerify(exactly = 1) { service.getEnterprises(name, client, uid, enterprise) }
    }

    @Test
    fun `GIVEN a call on getEnterprises WHEN service returns unauthorized code THEN return mapped response`() = runBlockingTest {
        val name = "test"
        val accessToken = "test"
        val client = "test"
        val uid = "test"
        val userSession = UserSession(accessToken, client, uid)
        val enterpriseResultResponse = EnterpriseResultResponse(
            enterprises = listOf(mockk(relaxed = true), mockk(relaxed = true))
        )
        val expected = Result.Success(enterpriseResultResponse.enterprises.toDomain())

        coEvery { service.getEnterprises(any(), any(), any(), any()) } returns enterpriseResultResponse

        val result = enterpriseApiDataSourceImpl.getEnterprises(name, userSession)

        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN a call on getEnterprises WHEN service returns error THEN return error`() = runBlockingTest {
        val name = "test"
        val accessToken = "test"
        val client = "test"
        val uid = "test"
        val userSession = UserSession(accessToken, client, uid)

        coEvery { service.getEnterprises(any(), any(), any(), any()) } throws IOException()

        val result = enterpriseApiDataSourceImpl.getEnterprises(name, userSession)

        assertTrue(result is Result.Error && result.exception is NetworkErrorException)
    }
}