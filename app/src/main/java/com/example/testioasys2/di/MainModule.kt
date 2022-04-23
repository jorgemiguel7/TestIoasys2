package com.example.testioasys2.di

import com.example.testioasys2.data.remote.enterprise.dataSource.EnterpriseApiDataSourceImpl
import com.example.testioasys2.data.remote.enterprise.dataSource.EnterpriseApiDataSource
import com.example.testioasys2.domain.repository.EnterpriseRepository
import com.example.testioasys2.data.repository.EnterpriseRepositoryImpl
import com.example.testioasys2.data.rest.EnterpriseService
import com.example.testioasys2.utils.Constants
import com.example.testioasys2.viewModel.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var mainModule = module {
    single<EnterpriseService> {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        Retrofit.Builder().baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(EnterpriseService::class.java)

    }

    single<EnterpriseRepository> { EnterpriseRepositoryImpl(get()) }
    single<EnterpriseApiDataSource> { EnterpriseApiDataSourceImpl(get()) }

    viewModel {
        MainViewModel(enterpriseRepository = get())
    }
}