package com.example.testioasys2.di

import com.example.testioasys2.data.dataSource.LoginApiDataSource
import com.example.testioasys2.data.repository.LoginRepository
import com.example.testioasys2.viewModel.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory <LoginRepository> { LoginApiDataSource() }

    viewModel {
        LoginViewModel(dataSource = get())
    }
}