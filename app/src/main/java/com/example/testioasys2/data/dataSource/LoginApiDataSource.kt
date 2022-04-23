package com.example.testioasys2.data.dataSource

import com.example.testioasys2.data.UserSession
import com.example.testioasys2.data.model.Result
import com.example.testioasys2.data.model.User

interface LoginApiDataSource {
    suspend fun doLogin(user: User):Result<UserSession>
}