package com.example.testioasys2.data.remote.login.dataSource

import com.example.testioasys2.data.remote.login.mapper.toUserSession
import com.example.testioasys2.data.remote.login.model.UserRequest
import com.example.testioasys2.data.remote.rest.LoginService
import com.example.testioasys2.data.remote.rest.retrofitWrapper
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class LoginApiDataSourceImplTest {
    public val service: LoginService = mockk()
    public lateinit var loginApiDataSourceImpl: LoginApiDataSourceImpl

    @Before
    fun setupTest(){
        loginApiDataSourceImpl = LoginApiDataSourceImpl(service)
    }

    @After
    fun resetMocks(){
        clearAllMocks()
    }

    @Test
    fun `Quando retornar sucesso`() = runBlockingTest {
        val email = "nada@gmail.com"
        val password = "12341234"
        val user = User(email, password)
        val userRequest = UserRequest(email, password)

        val response: Response<ResponseBody> = mockk()
        val expected = Result.Success(response.toUserSession())

        coEvery { service.doLogin(any()) } returns response

        val result = loginApiDataSourceImpl.doLogin(user)

        assertEquals(expected, result)
    }
}