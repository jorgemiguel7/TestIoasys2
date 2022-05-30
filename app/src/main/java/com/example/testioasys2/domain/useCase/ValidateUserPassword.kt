package com.example.testioasys2.domain.useCase

interface ValidateUserPassword {
    fun call(password: String): Boolean
}