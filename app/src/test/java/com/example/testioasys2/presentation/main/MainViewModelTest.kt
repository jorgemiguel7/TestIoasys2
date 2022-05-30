package com.example.testioasys2.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testioasys2.domain.enterprise.Enterprise
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.domain.use_case.GetEnterpriseList
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.lang.Exception
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val getEnterpriseList: GetEnterpriseList = mockk()
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(getEnterpriseList, TestCoroutineDispatcher())
    }

    @After
    fun resetMocks() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN a call on getEnterprise WHEN use case return success THEN post success data on getEnterpriseSuccess liveData`() {
        val name = "test"
        val userSession = mockk<UserSession>()
        val expected = mockk<List<Enterprise>>()

        coEvery { getEnterpriseList.call(any(), any()) } returns Result.Success(expected)

        viewModel.getEnterprise(name, userSession)

        coVerify(exactly = 1) { getEnterpriseList.call(name, userSession) }
        assertEquals(expected, viewModel.getEnterpriseSuccess.value)
        assertEquals(false, viewModel.searchLoad.value)
    }

    @Test
    fun `GIVEN a call on getEnterprise WHEN use case return error THEN post error on getEnterpriseErrorMessage liveData`() {
        val name = "test"
        val userSession = mockk<UserSession>()
        val expected = mockk<Exception>()

        coEvery { getEnterpriseList.call(any(), any()) } returns Result.Error(expected)

        viewModel.getEnterprise(name, userSession)

        coVerify(exactly = 1) { getEnterpriseList.call(name, userSession) }
        assertEquals(expected, viewModel.getEnterpriseSuccess.value)
        assertEquals(false, viewModel.searchLoad.value)
    }
}