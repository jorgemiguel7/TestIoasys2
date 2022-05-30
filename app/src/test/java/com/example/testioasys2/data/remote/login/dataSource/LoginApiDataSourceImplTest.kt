package com.example.testioasys2.data.remote.login.dataSource

import com.example.testioasys2.data.remote.login.mapper.toUserSession
import com.example.testioasys2.data.remote.rest.LoginService
import com.example.testioasys2.domain.exception.ServerErrorException
import com.example.testioasys2.domain.exception.UnauthorizedException
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import java.net.HttpURLConnection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class LoginApiDataSourceImplTest {
    private val service: LoginService = mockk()
    private lateinit var loginApiDataSourceImpl: LoginApiDataSourceImpl

    @Before
    fun setupTest() {
        mockkStatic(Response<ResponseBody>::toUserSession)
        loginApiDataSourceImpl = LoginApiDataSourceImpl(service)
    }

    @After
    fun resetMocks() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN a call on doLogin WHEN service returns success THEN return user session`() = runBlockingTest {
        val email = "nada@gmail.com"
        val password = "12341234"
        val user = User(email, password)

        val response: Response<ResponseBody> = Response.success<ResponseBody>(HttpURLConnection.HTTP_OK, mockk())
        val userSession: UserSession = mockk()
        val expected = Result.Success(userSession)

        coEvery { response.toUserSession() } returns userSession
        coEvery { service.doLogin(any()) } returns response

        val result = loginApiDataSourceImpl.doLogin(user)

        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN a call on doLogin WHEN service returns success AND unauthorized code THEN return unauthorized exception`() = runBlockingTest {
        val email = "nada@gmail.com"
        val password = "12341234"
        val user = User(email, password)

        val response: Response<ResponseBody> = Response.success<ResponseBody>(HttpURLConnection.HTTP_UNAUTHORIZED, mockk())

        coEvery { service.doLogin(any()) } returns response

        val result = loginApiDataSourceImpl.doLogin(user)

        assertTrue(result is Result.Error && result.exception is UnauthorizedException)
    }

    @Test
    fun `GIVEN a call on doLogin WHEN service returns success AND an unknown code THEN return server error exception`() = runBlockingTest {
        val email = "nada@gmail.com"
        val password = "12341234"
        val user = User(email, password)

        val response: Response<ResponseBody> = Response.success<ResponseBody>(HttpURLConnection.HTTP_BAD_REQUEST, mockk())

        coEvery { service.doLogin(any()) } returns response

        val result = loginApiDataSourceImpl.doLogin(user)

        assertTrue(result is Result.Error && result.exception is ServerErrorException)
    }
}