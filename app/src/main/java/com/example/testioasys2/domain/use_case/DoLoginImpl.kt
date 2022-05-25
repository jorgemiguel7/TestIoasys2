package com.example.testioasys2.domain.use_case

import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.repository.login.LoginRepository
import com.example.testioasys2.domain.result.Result

class DoLoginImpl(private val loginRepository: LoginRepository): DoLogin {
    override suspend fun call(user: User): Result<UserSession> {
        return loginRepository.doLogin(user)
    }
}