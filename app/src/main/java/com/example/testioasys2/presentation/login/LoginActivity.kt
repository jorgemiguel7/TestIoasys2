package com.example.testioasys2.presentation.login

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.testioasys2.R
import com.example.testioasys2.data.model.User
import com.example.testioasys2.databinding.ActivityLoginBinding
import com.example.testioasys2.presentation.main.MainActivity
import com.example.testioasys2.utils.LoadingDialog
import com.example.testioasys2.viewModel.login.LoginViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
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
//            LoadingDialog.finishLoading()
            finish()
        }

        viewModel.errorMessage.observe(this@LoginActivity){
            it?.let { message ->
                if (message == R.string.unauthorized_error_message){
                    loginEmailTextInputLayout.error = " "
                    loginPasswordTextInputLayout.error = getString(message)
                } else {
                    Snackbar.make(root, message, Snackbar.LENGTH_INDEFINITE)
                        .setTextColor(Color.WHITE)
                        .setActionTextColor(Color.WHITE)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                        .setBackgroundTint(Color.BLACK)
                        .setAction(getString(R.string.ok)){}
                        .show()
                }
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