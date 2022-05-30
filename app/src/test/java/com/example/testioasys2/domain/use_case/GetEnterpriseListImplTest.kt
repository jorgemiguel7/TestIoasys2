package com.example.testioasys2.domain.use_case

import com.example.testioasys2.domain.enterprise.Enterprise
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.repository.enterprise.EnterpriseRepository
import com.example.testioasys2.domain.result.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetEnterpriseListImplTest{
    private val enterpriseRepository: EnterpriseRepository = mockk()
    private lateinit var getEnterpriseListImpl: GetEnterpriseListImpl

    @Before
    fun setupTest(){
        getEnterpriseListImpl = GetEnterpriseListImpl(enterpriseRepository)
    }

    @After
    fun resetMocks() {
        clearAllMocks()
    }

    @Test
    fun `WHEN is called THEN it should return repository result`() = runBlockingTest {
        val name = "test"
        val accessToken = "test"
        val client = "test"
        val uid = "test"
        val userSession = UserSession(accessToken, client, uid)

        val enterprise = listOf<Enterprise>(mockk(relaxed = true))
        val expected = Result.Success(enterprise)

        coEvery { enterpriseRepository.getEnterprises(any(), any()) } returns expected

        val result = getEnterpriseListImpl.call(name, userSession)

        coVerify(exactly = 1) { enterpriseRepository.getEnterprises(name, userSession) }
        assertEquals(expected, result)
    }
}