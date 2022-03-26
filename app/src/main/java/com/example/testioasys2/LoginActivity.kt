package com.example.testioasys2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testioasys2.data.model.UserRequest
import com.example.testioasys2.data.repository.LoginApiDataSource
import com.example.testioasys2.databinding.ActivityLoginBinding
import com.example.testioasys2.utils.Constants
import com.example.testioasys2.utils.UserSession
import com.example.testioasys2.utils.Validator
import com.example.testioasys2.viewModel.login.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        viewModel = LoginViewModel.ViewModelFactory(LoginApiDataSource())
            .create(LoginViewModel::class.java)

        observer()
        toSend()


    }

    private fun toSend() = _binding.apply{
        loginEnterButton.setOnClickListener {
            if (!Validator.validateEmail(loginEmailTextInputLayout.editText?.text.toString())){
                loginEmailTextInputLayout.error = getString(R.string.login_invalid_email)
                return@setOnClickListener
            }
            if(!Validator.validatePassword(loginPasswordTextInputLayout.editText?.text.toString())){
                loginPasswordTextInputLayout.error = getString(R.string.login_fill_field)
                return@setOnClickListener
            }
            viewModel.login(
                UserRequest(
                    loginEmailTextInputLayout.editText?.text.toString(),
                    loginPasswordTextInputLayout.editText?.text.toString())
            )
        }
    }

    private fun observer() = _binding.apply {
        viewModel.success.observe(this@LoginActivity){ headers ->
            UserSession.setUserAccessCredentials(
                headers[Constants.ACCESS_TOKEN],
                headers[Constants.CLIENT],
                headers[Constants.UID])
        }

        viewModel.errorMessage.observe(this@LoginActivity){
            it?.let { message ->
                loginEmailTextInputLayout.error = " "
                loginPasswordTextInputLayout.error = getString(message)
            }
        }
    }
}