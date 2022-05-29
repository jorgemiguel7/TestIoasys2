package com.example.testioasys2.domain.use_case

import android.util.Patterns
import com.example.testioasys2.domain.model.EmailStatus
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ValidateUserEmailImplTest{

    private lateinit var validateUserEmailImpl: ValidateUserEmailImpl

    @Before
    fun setupTest(){
        validateUserEmailImpl = ValidateUserEmailImpl()
    }

    @After
    fun resetMocks() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN a call to the call function WHEN the email is empty THEN it should return EmailStatus blanck`() {
        val email = ""
        val expected = EmailStatus.BLANK
//        val expected2: EmailStatus = mockk(relaxed = true)


//        justRun { email.isNotBlank() }
//        justRun { email.isNotEmpty() }
//        every { Patterns.EMAIL_ADDRESS.matcher(any()).matches() } returns true
//        every { validateUserEmailImpl.call(any()) } returns expected2

        val result = validateUserEmailImpl.call(email)

        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN a call to the function called WHEN the email is blank, tab or enter THEN it should return blank EmailStatus`(){
        val email = " "
        val emailTab = "    "
        val emailEnter = "" +
                ""
        val expected = EmailStatus.BLANK

        val result = validateUserEmailImpl.call(email)
        val resultTab = validateUserEmailImpl.call(emailTab)
        val resultEnter = validateUserEmailImpl.call(emailEnter)

        assertEquals(expected, result)
        assertEquals(expected, resultTab)
        assertEquals(expected, resultEnter)
    }

    //Nao ta passando
    @Test
    fun `GIVEN a call to the function called WHEN the email is invalid THEN it should return invalid EmailStatus`(){
        val email = "teste.com"
        val expected = EmailStatus.INVALID

        val emailValido = !Patterns.EMAIL_ADDRESS.matcher(email).matches()

        val result = validateUserEmailImpl.call(email)

        assertTrue(emailValido && result == expected)
    }

    //Nao ta passando
    @Test
    fun `GIVEN a call to the function called WHEN the email is valid THEN it should return EmailStatus valid`(){
        val email = "teste@gmail.com"
        val expected = EmailStatus.VALID

        val result = validateUserEmailImpl.call(email)

        assertEquals(expected, result)
    }
}