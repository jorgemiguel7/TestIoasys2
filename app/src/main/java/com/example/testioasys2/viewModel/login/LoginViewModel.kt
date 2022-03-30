package com.example.testioasys2.viewModel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testioasys2.R
import com.example.testioasys2.data.LoginStatus
import com.example.testioasys2.data.model.UserRequest
import com.example.testioasys2.data.repository.LoginRepository
import okhttp3.Headers

class LoginViewModel(private val dataSource: LoginRepository): ViewModel(){
    val success = MutableLiveData<Headers>()
    val errorMessage = MutableLiveData<Int>()

    fun login(userRequest: UserRequest){
        dataSource.doLogin(userRequest){ loginStatus: LoginStatus ->
            when(loginStatus){
                is LoginStatus.Success ->{
                    success.value = loginStatus.userAccess
                }
                is LoginStatus.ApiError ->{
                    if (loginStatus.statusCode == 401){
                        errorMessage.value = R.string.login_error_message_401
                    } else {
                        errorMessage.value = R.string.login_server_connection_problems
                    }
                }
                is LoginStatus.ServerError ->{
                    errorMessage.value = R.string.login_internet_connection_failure
                }
            }
        }
    }

    class ViewModelFactory(private val dataSource: LoginRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
                return LoginViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewlModel class")
        }

    }
}