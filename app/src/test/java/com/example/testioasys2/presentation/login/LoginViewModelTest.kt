package com.example.testioasys2.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testioasys2.domain.model.EmailStatus
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.domain.useCase.DoLogin
import com.example.testioasys2.domain.useCase.ValidateUserEmail
import com.example.testioasys2.domain.useCase.ValidateUserPassword
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest{
    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val doLogin: DoLogin = mockk()
    private val validateUserEmail: ValidateUserEmail = mockk()
    private val validateUserPassword: ValidateUserPassword = mockk()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel(doLogin, validateUserEmail, validateUserPassword)
    }

    @After
    fun resetMocks() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN a call on login WHEN the use case returns success THEN post success data to loginSuccess liveData`() {
        val user = User("test@gmail.com", "1234")
        val expected = mockk<UserSession>()

        coEvery { validateUserEmail.call(any()) } returns EmailStatus.VALID
        coEvery { validateUserPassword.call(any()) } returns true
        coEvery { doLogin.call(any()) } returns Result.Success(expected)

        viewModel.login(user)

        coVerify(exactly = 1) { doLogin.call(user) }
        assertEquals(false, viewModel.loading.value)
        assertEquals(expected, viewModel.loginSuccess.value)
    }

    @Test
    fun `GIVEN a call on login WHEN the use case returns error THEN post error on loginErrorMessage liveData`(){
        val user = User("test@gmail.com", "1234")
        val expected = mockk<Exception>()

        coEvery { validateUserEmail.call(any()) } returns EmailStatus.VALID
        coEvery { validateUserPassword.call(any()) } returns true
        coEvery { doLogin.call(any()) } returns Result.Error(expected)

        viewModel.login(user)

        coVerify(exactly = 1) { doLogin.call(user) }
        assertEquals(false, viewModel.loading.value)
        assertEquals(expected, viewModel.loginErrorMessage.value)
    }

    @Test
    fun `GIVEN a call on login WHEN the email is different from valid and password true THEN the use case will not be called`(){
        val user = User("test@gmail.com", "1234")

        coEvery { validateUserEmail.call(any()) } returns EmailStatus.INVALID
        coEvery { validateUserPassword.call(any()) } returns true

        viewModel.login(user)

        coVerify(exactly = 0) { doLogin.call(user) }
    }

    @Test
    fun `GIVEN a call on login WHEN the email is valid and the password is false THEN the use case will not be called`(){
        val user = User("test@gmail.com", "1234")

        coEvery { validateUserEmail.call(any()) } returns EmailStatus.VALID
        coEvery { validateUserPassword.call(any()) } returns false

        viewModel.login(user)

        coVerify(exactly = 0) { doLogin.call(user) }
    }
}