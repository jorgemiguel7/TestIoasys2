package com.example.testioasys2.utils

import android.util.Log
import android.util.Patterns

object Validator {
    fun validateEmail(email: String): Boolean{
        return !(email.isEmpty() || email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    fun validatePassword(password: String): Boolean{
        return !(password.isEmpty() || password.isBlank())
    }
}