package com.example.testioasys2.di

import com.example.testioasys2.data.dataSource.EnterpriseApiDataSource
import com.example.testioasys2.data.dataSource.LoginApiDataSource
import com.example.testioasys2.data.repository.EnterpriseRepository
import com.example.testioasys2.data.repository.LoginRepository
import com.example.testioasys2.viewModel.login.LoginViewModel
import com.example.testioasys2.viewModel.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var mainModule = module {
    factory <EnterpriseRepository> {EnterpriseApiDataSource()}

    viewModel {
        MainViewModel(dataSource = get())
    }
}