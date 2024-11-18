package com.mobdeve.senateelectioninfo.auth.view.sign_up

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.senateelectioninfo.MainActivity
import com.mobdeve.senateelectioninfo.auth.AuthValidator
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.auth.view.log_in.LogInActivity
import com.mobdeve.senateelectioninfo.databinding.ActivitySignupBinding

class SignUpActivity: AppCompatActivity() {

    private lateinit var viewModel: SignUpViewModel

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = SignUpViewModelFactory(AccountServiceImpl.getInstance(), application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SignUpViewModel::class.java)

        binding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val inputFirstName = binding.inputSignUpFirstName
        val inputLastName = binding.inputSignUpLastName
        val inputEmail = binding.inputSignUpEmail
        val inputPassword = binding.inputSignUpPassword

        val imgLogo = binding.imgSignUpLogo
        val progressBar = binding.progressBarSignUp

        inputFirstName.doOnTextChanged { text, _, _, _ ->
            viewModel.updateFirstName(text.toString())
            binding.inputSignUpFirstNameLayout.error = ""
        }

        inputLastName.doOnTextChanged { text, _, _, _ ->
            viewModel.updateLastName(text.toString())
            binding.inputSignUpLastNameLayout.error = ""
        }

        inputEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.updateEmail(text.toString())
            binding.inputSignUpEmailLayout.error = ""
        }

        inputPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.updatePassword(text.toString())
            binding.inputSignUpPasswordLayout.error = ""
        }

        binding.btnSignUp.setOnClickListener {
            if (inputFirstName.text?.isBlank() == true || inputLastName.text?.isBlank() == true ||
                !AuthValidator.isValidEmail(inputEmail.text.toString()) || !AuthValidator.isValidPassword(inputPassword.text.toString())) {
                if (inputFirstName.text?.isBlank() == true) {
                    binding.inputSignUpFirstNameLayout.error = "Please enter your first name"
                }

                if (inputLastName.text?.isBlank() == true) {
                    binding.inputSignUpLastNameLayout.error = "Please enter your last name"
                }

                if (!AuthValidator.isValidEmail(inputEmail.text.toString())) {
                    binding.inputSignUpEmailLayout.error = "Invalid email address"
                }

                if (!AuthValidator.isValidPassword(inputPassword.text.toString())) {
                    binding.inputSignUpPasswordLayout.error = "Password must contain at least 8 characters"
                }

                return@setOnClickListener
            }

            imgLogo.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE

            viewModel.onSignUpClick { isSuccess ->
                imgLogo.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE

                Log.d("SignUpActivity", "isSuccess: $isSuccess")
                if (isSuccess) {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.txtLoginLink.setOnClickListener {
            val i = Intent(applicationContext, LogInActivity::class.java)
            startActivity(i)
        }
    }
}