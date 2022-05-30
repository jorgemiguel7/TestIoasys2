package com.example.testioasys2.domain.use_case

import com.example.testioasys2.domain.model.EmailStatus
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.regex.Pattern

class ValidateUserEmailImplTest{

    private val emailPattern = mockk<Pattern>()
    private lateinit var validateUserEmailImpl: ValidateUserEmailImpl

    @Before
    fun setupTest(){
        validateUserEmailImpl = ValidateUserEmailImpl(emailPattern)
    }

    @After
    fun resetMocks() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN a call WHEN the email is empty THEN it should return blank EmailStatus`() {
        val email = ""
        val expected = EmailStatus.BLANK

        val result = validateUserEmailImpl.call(email)

        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN a call WHEN the email is blank THEN it should return blank EmailStatus`(){
        val email = " "
        val expected = EmailStatus.BLANK

        val result = validateUserEmailImpl.call(email)

        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN a call WHEN the email is invalid THEN it should return invalid EmailStatus`(){
        val email = "teste.com"
        val expected = EmailStatus.INVALID

        coEvery { emailPattern.matcher(any()).matches() } returns false

        val result = validateUserEmailImpl.call(email)

        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN a call WHEN the email is valid THEN it should return EmailStatus valid`(){
        val email = "teste@gmail.com"
        val expected = EmailStatus.VALID

        coEvery { emailPattern.matcher(any()).matches() } returns true

        val result = validateUserEmailImpl.call(email)

        assertEquals(expected, result)
    }
}