package com.example.testioasys2.data.repository

import com.example.testioasys2.data.LoginStatus
import com.example.testioasys2.data.model.UserRequest

interface LoginRepository {
    fun doLogin(userRequest: UserRequest, access: (loginStatus: LoginStatus) -> Unit)
}