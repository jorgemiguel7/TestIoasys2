package com.example.testioasys2.data.repository.login

import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.data.remote.login.dataSource.LoginApiDataSource
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.repository.login.LoginRepository

class LoginRepositoryImpl(private val apiDataSource: LoginApiDataSource): LoginRepository {
    override suspend fun doLogin(user: User): Result<UserSession> {
        return apiDataSource.doLogin(user)
    }
}