package com.example.testioasys2.domain.use_case

import com.example.testioasys2.domain.exception.InvalidLoginException
import com.example.testioasys2.domain.model.EmailStatus
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.repository.login.LoginRepository
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
class DoLoginImplTest{
    private val validateUserEmail: ValidateUserEmail = mockk()
    private val validateUserPassword: ValidateUserPassword = mockk()
    private val loginRepository: LoginRepository = mockk()

    private lateinit var doLoginImpl: DoLoginImpl

    @Before
    fun setupTest(){
        doLoginImpl = DoLoginImpl(validateUserEmail, validateUserPassword, loginRepository)
    }

    @After
    fun resetMocks(){
        clearAllMocks()
    }

    @Test
    fun `WHEN email and password are valid THEN return repository result`() = runBlockingTest{
        val user = User("test@gmail.com", "1234")
        val expected = Result.Success(mockk<UserSession>())

        coEvery { validateUserEmail.call(any()) } returns EmailStatus.VALID
        coEvery { validateUserPassword.call(any()) } returns true
        coEvery { loginRepository.doLogin(any()) } returns expected

        val result = doLoginImpl.call(user)

        assertEquals(expected, result)
    }

    @Test
    fun `WHEN email is invalid and password valid THEN return invalid login exception`() = runBlockingTest{
        val user = User("test@gmail.com", "1234")
        val expected = Result.Error(InvalidLoginException)

        coEvery { validateUserEmail.call(any()) } returns EmailStatus.INVALID
        coEvery { validateUserPassword.call(any()) } returns true
        coEvery { loginRepository.doLogin(any()) } returns expected

        val result = doLoginImpl.call(user)

        assertEquals(expected, result)
    }

    @Test
    fun `WHEN the email is valid and the password is invalid THEN return invalid login exception`() = runBlockingTest{
        val user = User("test@gmail.com", "1234")
        val expected = Result.Error(InvalidLoginException)

        coEvery { validateUserEmail.call(any()) } returns EmailStatus.VALID
        coEvery { validateUserPassword.call(any()) } returns false
        coEvery { loginRepository.doLogin(any()) } returns expected

        val result = doLoginImpl.call(user)

        assertEquals(expected, result)
    }
}