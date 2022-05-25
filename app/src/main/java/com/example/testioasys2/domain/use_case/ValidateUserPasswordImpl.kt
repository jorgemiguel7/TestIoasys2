package com.example.testioasys2.domain.use_case

class ValidateUserPasswordImpl: ValidateUserPassword {
    override fun call(password: String): Boolean {
        return !(password.isEmpty() || password.isBlank())
    }
}