package com.example.testioasys2.domain.use_case

import android.util.Patterns
import com.example.testioasys2.domain.model.EmailStatus

class ValidateUserEmailImpl: ValidateUserEmail {
    override fun call(email: String): EmailStatus {
        return when{
            email.isEmpty() || email.isBlank() -> EmailStatus.BLANK
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> EmailStatus.INVALID
            else -> EmailStatus.VALID
        }
    }
}