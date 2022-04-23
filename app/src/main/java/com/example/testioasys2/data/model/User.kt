package com.example.testioasys2.data.model

import android.util.Patterns

data class User(
    val email: String,
    val password: String
){
    fun validateEmail(): EmailStatus{
        return when{
            email.isEmpty() || email.isBlank() -> EmailStatus.BLANK
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> EmailStatus.INVALID
            else -> EmailStatus.VALID
        }
    }

    fun validatePassword(): Boolean{
        return !(password.isEmpty() || password.isBlank())
    }
}