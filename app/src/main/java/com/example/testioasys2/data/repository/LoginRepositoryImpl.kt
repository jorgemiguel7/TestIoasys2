package com.example.testioasys2.data.repository

import com.example.testioasys2.data.UserSession
import com.example.testioasys2.data.dataSource.LoginApiDataSource
import com.example.testioasys2.data.model.Result
import com.example.testioasys2.data.model.User

class LoginRepositoryImpl(private val apiDataSource: LoginApiDataSource): LoginRepository {
    override suspend fun doLogin(user: User): Result<UserSession> {
        return apiDataSource.doLogin(user)
    }
}