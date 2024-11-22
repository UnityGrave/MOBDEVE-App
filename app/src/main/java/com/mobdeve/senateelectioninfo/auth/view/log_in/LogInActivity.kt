package com.mobdeve.senateelectioninfo.auth.view.log_in

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.senateelectioninfo.MainActivity
import com.mobdeve.senateelectioninfo.auth.AuthValidator
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.databinding.ActivityLoginBinding
import com.mobdeve.senateelectioninfo.auth.view.sign_up.SignUpActivity
import com.mobdeve.senateelectioninfo.auth.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogInActivity: AppCompatActivity() {

    private lateinit var viewModel: LogInViewModel
    private lateinit var binding: ActivityLoginBinding

    private val accountService = AccountServiceImpl.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = LogInViewModelFactory(accountService, application)
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
                    checkTwoFactorAuthStatus()
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

    private fun checkTwoFactorAuthStatus() {
        CoroutineScope(Dispatchers.IO).launch {
            val userProfile = accountService.getProfile()

            withContext(Dispatchers.Main) {
                if (userProfile != null && userProfile.authenticator == "enabled") {
                    showTwoFactorAuthDialog()
                } else {
                    navigateToMainActivity()
                }
            }
        }
    }


    private fun showTwoFactorAuthDialog() {
        val builder = AlertDialog.Builder(this)

        // Set the title and message with centered gravity
        builder.setTitle("Two-Factor Authentication")
        builder.setMessage("Enter the code")

        val input = android.widget.EditText(this)
        input.gravity = android.view.Gravity.CENTER

        val layoutParams = android.widget.LinearLayout.LayoutParams(
            android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
            android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = layoutParams

        val width = (resources.displayMetrics.widthPixels * 0.7).toInt()
        input.layoutParams.width = width

        builder.setView(input)

        val dialog = builder.create()
        dialog.setOnShowListener {
            val titleTextView = dialog.findViewById<TextView>(android.R.id.title)
            val messageTextView = dialog.findViewById<TextView>(android.R.id.message)

            titleTextView?.gravity = Gravity.CENTER
            messageTextView?.gravity = Gravity.CENTER
        }

        builder.setPositiveButton("Verify") { dialog, _ ->
            val code = input.text.toString()
            verifyTwoFactorAuthCode(code)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }


    private fun verifyTwoFactorAuthCode(code: String) {
        if (isCodeValid(code)) {
            navigateToMainActivity()
        } else {
            Toast.makeText(this, "Invalid 2FA code. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isCodeValid(code: String): Boolean {
        return code == "123456"
    }

    private fun navigateToMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}