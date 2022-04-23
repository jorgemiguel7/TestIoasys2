package com.example.testioasys2.di

import com.example.testioasys2.data.remote.login.dataSource.LoginApiDataSource
import com.example.testioasys2.data.remote.login.dataSource.LoginApiDataSourceImpl
import com.example.testioasys2.domain.repository.LoginRepository
import com.example.testioasys2.data.repository.LoginRepositoryImpl
import com.example.testioasys2.data.rest.LoginService
import com.example.testioasys2.utils.Constants
import com.example.testioasys2.viewModel.login.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val loginModule = module {
    single<LoginService> {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        Retrofit.Builder().baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(LoginService::class.java)

    }

    single <LoginRepository> { LoginRepositoryImpl(get()) }
    single <LoginApiDataSource> { LoginApiDataSourceImpl(get()) }

    viewModel {
        LoginViewModel(loginRepository = get())
    }
}