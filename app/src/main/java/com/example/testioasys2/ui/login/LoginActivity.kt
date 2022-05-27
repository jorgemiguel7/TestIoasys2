package com.example.testioasys2.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.testioasys2.R
import com.example.testioasys2.databinding.ActivityLoginBinding
import com.example.testioasys2.domain.exception.*
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.ui.main.MainActivity
import com.example.testioasys2.presentation.login.LoginViewModel
import com.example.testioasys2.utils.createLoadingDialog
import com.example.testioasys2.utils.showAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()
    private val loadingDialog by lazy { createLoadingDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListener()
        clearErrorMessageFromTextInput()
    }

    private fun setupListener() {
        binding.loginEnterButton.setOnClickListener {
            viewModel.login(
                User(
                    binding.loginEmailTextInputLayout.editText?.text?.toString().orEmpty(),
                    binding.loginPasswordTextInputLayout.editText?.text?.toString().orEmpty()
                )
            )
        }
    }

    private fun setupObservers() {
        binding.apply {
            viewModel.loginSuccess.observe(this@LoginActivity) { session ->
                val intent = MainActivity.getStartIntent(this@LoginActivity, session)
                startActivity(intent)
                finish()
            }

            viewModel.loginErrorMessage.observe(this@LoginActivity) { throwable ->
                when (throwable) {
                    is UnauthorizedException -> {
                        loginEmailTextInputLayout.error = " "
                        loginPasswordTextInputLayout.error =
                            getString(R.string.unauthorized_error_message)
                    }
                    is InvalidLoginException -> loginEmailTextInputLayout.error = getString(R.string.login_invalid_email)
                    is NetworkErrorException -> showAlertDialog(R.string.internet_connection_failure)
                    is ServerErrorException -> showAlertDialog(R.string.server_connection_problems)
                    else -> showAlertDialog(R.string.generic_failure)
                }
            }

            viewModel.passwordErrorMessage.observe(this@LoginActivity){ errorMessageId ->
                loginPasswordTextInputLayout.error = errorMessageId?.let { getString(errorMessageId) }.orEmpty()
            }

            viewModel.emailErrorMessage.observe(this@LoginActivity) { errorMessageId ->
                loginEmailTextInputLayout.error =
                    errorMessageId?.let { getString(errorMessageId) }.orEmpty()
            }

            viewModel.loading.observe(this@LoginActivity) { isLoading ->
                if (isLoading) loadingDialog.show()
                else loadingDialog.dismiss()
            }
        }
    }

    private fun clearErrorMessageFromTextInput() {
        binding.apply {
            loginEmailTextInputLayout.editText?.doAfterTextChanged {
                loginEmailTextInputLayout.error = ""
            }
            loginPasswordTextInputLayout.editText?.doAfterTextChanged {
                loginPasswordTextInputLayout.error = ""
            }
        }
    }
}