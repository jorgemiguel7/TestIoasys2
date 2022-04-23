package com.example.testioasys2.data.repository

import com.example.testioasys2.data.UserSession
import com.example.testioasys2.data.model.Result
import com.example.testioasys2.data.model.User

interface LoginRepository {
    suspend fun doLogin(user: User):Result<UserSession>
}