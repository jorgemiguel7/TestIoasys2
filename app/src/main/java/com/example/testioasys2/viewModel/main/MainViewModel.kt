package com.example.testioasys2.viewModel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testioasys2.R
import com.example.testioasys2.data.EnterpriseStatus
import com.example.testioasys2.data.model.Enterprise
import com.example.testioasys2.data.repository.EnterpriseRepository

class MainViewModel(private val dataSource: EnterpriseRepository): ViewModel() {
    val success = MutableLiveData<List<Enterprise>>()
    val errorMessage = MutableLiveData<Int>()

    fun getEnterprise(name: String?){
        dataSource.getEnterprises(name){ enterpriseStatus: EnterpriseStatus ->
            when(enterpriseStatus){
                is EnterpriseStatus.Success ->{
                    success.value = enterpriseStatus.enterprises
                }
                is EnterpriseStatus.ApiError ->{
                    if (enterpriseStatus.statusCode == 401){
                        errorMessage.value = R.string.main_http_401_Unauthorized
                    } else {
                        errorMessage.value = R.string.main_error_connecting_to_server
                    }
                }
                is EnterpriseStatus.ServerError ->{
                    errorMessage.value = R.string.main_internet_connection_failure
                }
            }
        }
    }
}