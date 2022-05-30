package com.example.testioasys2.domain.useCase

class ValidateUserPasswordImpl: ValidateUserPassword {
    override fun call(password: String): Boolean {
        return !(password.isEmpty() || password.isBlank())
    }
}