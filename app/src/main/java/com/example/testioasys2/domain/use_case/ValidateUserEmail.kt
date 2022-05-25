package com.example.testioasys2.domain.use_case

import com.example.testioasys2.domain.model.EmailStatus

interface ValidateUserEmail {
    fun call(email: String): EmailStatus
}