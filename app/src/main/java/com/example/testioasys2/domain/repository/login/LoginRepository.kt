package com.example.testioasys2.domain.repository.login

import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.domain.model.User

interface LoginRepository {
    suspend fun doLogin(user: User): Result<UserSession>
}