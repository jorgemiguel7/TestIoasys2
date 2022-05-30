package com.example.testioasys2.domain.useCase

import com.example.testioasys2.domain.model.EmailStatus

interface ValidateUserEmail {
    fun call(email: String): EmailStatus
}