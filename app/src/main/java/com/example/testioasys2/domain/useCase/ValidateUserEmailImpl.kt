package com.example.testioasys2.domain.useCase

import com.example.testioasys2.domain.model.EmailStatus
import java.util.regex.Pattern

class ValidateUserEmailImpl(private val emailPattern: Pattern): ValidateUserEmail {
    override fun call(email: String): EmailStatus {
        return when{
            email.isEmpty() || email.isBlank() -> EmailStatus.BLANK
            !emailPattern.matcher(email).matches() -> EmailStatus.INVALID
            else -> EmailStatus.VALID
        }
    }
}