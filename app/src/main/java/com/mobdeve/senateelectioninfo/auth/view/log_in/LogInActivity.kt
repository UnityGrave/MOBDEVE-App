package com.mobdeve.senateelectioninfo.auth.view.log_in

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.senateelectioninfo.MainActivity
import com.mobdeve.senateelectioninfo.auth.AuthValidator
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.databinding.ActivityLoginBinding
import com.mobdeve.senateelectioninfo.auth.view.sign_up.SignUpActivity

class LogInActivity: AppCompatActivity() {

    private lateinit var viewModel: LogInViewModel

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = LogInViewModelFactory(AccountServiceImpl.getInstance(), application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LogInViewModel::class.java)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val inputEmail = binding.inputLogInEmail
        val inputPassword = binding.inputLogInPassword

        val imgLogo = binding.imgLogInLogo
        val progressBar = binding.progressBarLogIn

        inputEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.updateEmail(text.toString())
            binding.inputLogInEmailLayout.error = null
        }

        inputPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.updatePassword(text.toString())
            binding.inputLogInPasswordLayout.error = null
        }

        binding.btnLogIn.setOnClickListener {
            if (!AuthValidator.isValidEmail(inputEmail.text.toString()) || !AuthValidator.isValidPassword(inputPassword.text.toString())) {
                if (!AuthValidator.isValidEmail(inputEmail.text.toString())) {
                    binding.inputLogInEmailLayout.error = "Invalid email address"
                }

                if (!AuthValidator.isValidPassword(inputPassword.text.toString())) {
                    binding.inputLogInPasswordLayout.error = "Password must contain at least 8 characters"
                }

                return@setOnClickListener
            }

            imgLogo.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE

            viewModel.onLogInClick { isSuccess ->

                imgLogo.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE

                if (isSuccess) {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.txtSignUpLink.setOnClickListener {
            val i = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(i)
        }
    }
}