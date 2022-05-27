package com.example.testioasys2.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testioasys2.domain.enterprise.Enterprise
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.domain.use_case.GetEnterpriseList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val getEnterpriseList: GetEnterpriseList,
    private val dispatcher: CoroutineContext = Dispatchers.IO
): ViewModel() {
    private val _getEnterpriseSuccess = MutableLiveData<List<Enterprise>>()
    val getEnterpriseSuccess: LiveData<List<Enterprise>> = _getEnterpriseSuccess
    private val _getEnterpriseErrorMessage = MutableLiveData<Throwable>()
    val getEnterpriseErrorMessage: LiveData<Throwable> = _getEnterpriseErrorMessage
    private val _searchLoad = MutableLiveData<Boolean>()
    val searchLoad: LiveData<Boolean> = _searchLoad

    fun getEnterprise(name: String?, userSession: UserSession){
        viewModelScope.launch(dispatcher) {
            _searchLoad.postValue(true)
            when(val result = getEnterpriseList.call(name, userSession)){
                is Result.Success -> {
                    _searchLoad.postValue(false)
                    _getEnterpriseSuccess.postValue(result.data)
                }
                is Result.Error -> {
                    _searchLoad.postValue(false)
                    _getEnterpriseErrorMessage.postValue(result.exception)
                }
            }
        }
    }
}