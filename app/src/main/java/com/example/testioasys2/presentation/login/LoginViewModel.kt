package com.example.testioasys2.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testioasys2.R
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.model.EmailStatus
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.result.Result
import com.example.testioasys2.domain.use_case.DoLogin
import com.example.testioasys2.domain.use_case.ValidateUserEmail
import com.example.testioasys2.domain.use_case.ValidateUserPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
    private val doLogin: DoLogin,
    private val validateUserEmail: ValidateUserEmail,
    private val validateUserPassword: ValidateUserPassword,
    private val dispatcher: CoroutineContext = Dispatchers.IO
): ViewModel(){
    private val _success = MutableLiveData<UserSession>()
    val success: LiveData<UserSession> = _success

    private val _errorMessage = MutableLiveData<Throwable>()
    val errorMessage: LiveData<Throwable> = _errorMessage

    private val _emailErrorMessage = MutableLiveData<Int?>()
    val emailErrorMessage: LiveData<Int?> = _emailErrorMessage
    private val _passwordErrorMessage = MutableLiveData<Int?>()
    val passwordErrorMessage: LiveData<Int?> = _passwordErrorMessage
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun login(user: User){
        viewModelScope.launch(dispatcher) {
            val emailStatus = validateEmail(user)
            val isPasswordValid = validatePassword(user)

            if (emailStatus == EmailStatus.VALID && isPasswordValid){
                _loading.postValue(true)
                when(val result = doLogin.call(user)){
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

    private fun validatePassword(user: User): Boolean {
        val validPassword = validateUserPassword.call(user.password)
        if (validPassword) _passwordErrorMessage.postValue(null)
        else _passwordErrorMessage.postValue(R.string.login_fill_field)
        return validPassword
    }

    private fun validateEmail(user: User): EmailStatus {
        val emailStatus = validateUserEmail.call(user.email)
        when(emailStatus){
            EmailStatus.VALID -> _emailErrorMessage.postValue(null)
            EmailStatus.INVALID -> _emailErrorMessage.postValue(R.string.login_invalid_email)
            EmailStatus.BLANK -> _emailErrorMessage.postValue(R.string.login_fill_field)
        }
        return emailStatus
    }
}