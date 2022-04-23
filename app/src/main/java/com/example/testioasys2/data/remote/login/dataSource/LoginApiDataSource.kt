package com.example.testioasys2.data.remote.login.dataSource

import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.domain.model.User

interface LoginApiDataSource {
    suspend fun doLogin(user: User): Result<UserSession>
}