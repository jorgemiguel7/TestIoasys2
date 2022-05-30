package com.example.testioasys2.domain.use_case

import io.mockk.clearAllMocks
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateUserPasswordImplTest{

    private lateinit var validateUserPasswordImpl: ValidateUserPasswordImpl

    @Before
    fun setupTest(){
        validateUserPasswordImpl = ValidateUserPasswordImpl()
    }

    @After
    fun resetMocks(){
        clearAllMocks()
    }

    @Test
    fun `WHEN the password is empty THEN it should return false`(){
        val password = ""

        val result = validateUserPasswordImpl.call(password)

        assertEquals(false, result)
    }

    @Test
    fun `WHEN the password is not empty THEN it should return true`(){
        val password = "test"

        val result = validateUserPasswordImpl.call(password)

        assertEquals(true, result)
    }
}