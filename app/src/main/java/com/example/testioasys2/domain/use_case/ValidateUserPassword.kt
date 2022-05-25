package com.example.testioasys2.domain.use_case

interface ValidateUserPassword {
    fun call(password: String): Boolean
}