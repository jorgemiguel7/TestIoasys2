package com.example.testioasys2.data.repository.enterprise

import com.example.testioasys2.data.remote.enterprise.dataSource.EnterpriseApiDataSource
import com.example.testioasys2.domain.enterprise.Enterprise
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EnterpriseRepositoryImplTest{

    private val apiDataSource: EnterpriseApiDataSource = mockk()
    private lateinit var enterpriseRepositoryImpl: EnterpriseRepositoryImpl

    @Before
    fun setupTest(){
        enterpriseRepositoryImpl = EnterpriseRepositoryImpl(apiDataSource)
    }

    @After
    fun resetMocks(){
        clearAllMocks()
    }

    @Test
    fun `GIVEN a call to getEnterprises THEN return apiDataSource result`() = runBlockingTest{
        val name = "test"
        val userSession = UserSession("test", "test", "test")
        val expected = mockk<Result<List<Enterprise>>>()

        coEvery { apiDataSource.getEnterprises(any(), any()) } returns expected

        val result = enterpriseRepositoryImpl.getEnterprises(name, userSession)

        assertEquals(expected, result)
    }
}