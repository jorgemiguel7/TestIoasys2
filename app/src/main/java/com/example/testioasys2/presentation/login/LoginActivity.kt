package com.example.testioasys2.presentation.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.testioasys2.R
import com.example.testioasys2.databinding.ActivityLoginBinding
import com.example.testioasys2.domain.exception.NetworkErrorException
import com.example.testioasys2.domain.exception.ServerErrorException
import com.example.testioasys2.domain.exception.UnauthorizedException
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.presentation.main.MainActivity
import com.example.testioasys2.presentation.viewModel.login.LoginViewModel
import com.example.testioasys2.utils.LoadingDialog
import com.example.testioasys2.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()
        toSend()
        clearErrorMessageFromTextInput()
    }

    private fun toSend() = binding.apply{
        loginEnterButton.setOnClickListener {
            viewModel.login(
                User(
                    loginEmailTextInputLayout.editText?.text?.toString().orEmpty(),
                    loginPasswordTextInputLayout.editText?.text?.toString().orEmpty())
            )
        }
    }

    private fun observer() = binding.apply {
        viewModel.success.observe(this@LoginActivity){ session ->
            val intent = MainActivity.getStartIntent(this@LoginActivity, session)
            startActivity(intent)
            finish()
        }

        viewModel.errorMessage.observe(this@LoginActivity){ throwable ->
            when(throwable){
                is UnauthorizedException -> { loginEmailTextInputLayout.error = " "
                    loginPasswordTextInputLayout.error = getString(R.string.unauthorized_error_message)
                }
                is NetworkErrorException -> showAlertDialog(R.string.internet_connection_failure)
                is ServerErrorException -> showAlertDialog(R.string.server_connection_problems)
                else -> showAlertDialog(R.string.generic_failure)
            }
        }

        viewModel.emailErrorMessage.observe(this@LoginActivity){ errorMessageId ->
            loginEmailTextInputLayout.error = errorMessageId?.let { getString(errorMessageId) }.orEmpty()
        }

        viewModel.passwordErrorMessage.observe(this@LoginActivity){ errorMessageId ->
            loginPasswordTextInputLayout.error = errorMessageId?.let { getString(errorMessageId) }.orEmpty()
        }

        viewModel.loading.observe(this@LoginActivity){ isLoading ->
            if (isLoading)LoadingDialog.startLoading(this@LoginActivity)
            else LoadingDialog.finishLoading()
        }
    }

    private fun clearErrorMessageFromTextInput() = binding.apply {
        loginEmailTextInputLayout.editText?.doAfterTextChanged {
            loginEmailTextInputLayout.error = ""
        }
        loginPasswordTextInputLayout.editText?.doAfterTextChanged {
            loginPasswordTextInputLayout.error = ""
        }
    }
}