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
    private val _success = MutableLiveData<List<Enterprise>>()
    val success: LiveData<List<Enterprise>> = _success
    private val _errorMessage = MutableLiveData<Throwable>()
    val errorMessage: LiveData<Throwable> = _errorMessage
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getEnterprise(name: String?, userSession: UserSession){
        viewModelScope.launch(dispatcher) {
            _loading.postValue(true)
            when(val result = getEnterpriseList.call(name, userSession)){
                is Result.Success -> {
                    _loading.postValue(false)
                    _success.postValue(result.data)
                }
                is Result.Error -> {
                    _loading.postValue(false)
                    _errorMessage.postValue(result.exception)
                }
            }
        }
    }
}