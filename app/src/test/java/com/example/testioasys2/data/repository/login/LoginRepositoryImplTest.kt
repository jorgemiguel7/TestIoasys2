package com.example.testioasys2.data.repository.login

import com.example.testioasys2.data.remote.login.dataSource.LoginApiDataSource
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginRepositoryImplTest{
    private val apiDataSource: LoginApiDataSource = mockk()
    private lateinit var loginRepositoryImpl: LoginRepositoryImpl

    @Before
    fun setupTest(){
        loginRepositoryImpl = LoginRepositoryImpl(apiDataSource)
    }

    @After
    fun resetMocks(){
        clearAllMocks()
    }

    @Test
    fun `GIVEN a call to doLogin THEN return apiDataSource result`() = runBlockingTest{
        val user = mockk<User>()
        val expected = mockk<Result<UserSession>>()

        coEvery { apiDataSource.doLogin(any()) } returns expected

        val result = loginRepositoryImpl.doLogin(user)

        assertEquals(expected, result)
    }
}