package com.mobdeve.senateelectioninfo.auth.view.sign_up

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.mobdeve.senateelectioninfo.MainActivity
import com.mobdeve.senateelectioninfo.auth.AuthValidator
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.auth.view.log_in.LogInActivity
import com.mobdeve.senateelectioninfo.databinding.ActivitySignupBinding

class SignUpActivity: AppCompatActivity() {

    private lateinit var viewModel: SignUpViewModel

    private lateinit var binding: ActivitySignupBinding

    private lateinit var pagerAdapter: SignUpPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = SignUpViewModelFactory(AccountServiceImpl.getInstance(), application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SignUpViewModel::class.java)

        binding = ActivitySignupBinding.inflate(layoutInflater)

        pagerAdapter = SignUpPagerAdapter(this)
        binding.viewPagerSignUp.adapter = pagerAdapter
        binding.signUpProgress.progress = 1

        setContentView(binding.root)

        val inputFirstName = viewModel.firstName.value
        val inputLastName = viewModel.lastName.value
        val inputBirthday = viewModel.birthday.value
        val inputContactNumber = viewModel.contactNumber.value
        val inputEmail = viewModel.email.value
        val inputPassword = viewModel.password.value

        val imgLogo = binding.imgSignUpLogo
        val progressBar = binding.progressBarSignUp

        binding.btnSignUp.text = "Next"

        var currentFragment: Fragment? = null
        binding.viewPagerSignUp.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 1 &&
                    (viewModel.firstName.value.isBlank() || viewModel.lastName.value.isBlank())
                    ) {
                    (pagerAdapter.getFragment(0) as SignUpPage1Fragment).setErrorMessages(
                        if (viewModel.firstName.value.isBlank()) "Please enter your first name" else null,
                        if (viewModel.lastName.value.isBlank()) "Please enter your last name" else null
                    )
                    binding.viewPagerSignUp.setCurrentItem(0, true)

                    return
                }

                if (position == 2 &&
                    (viewModel.birthday.value.isBlank() || viewModel.contactNumber.value.isBlank() ||
                     viewModel.contactNumber.value.length < 11)
                ) {
                    (pagerAdapter.getFragment(1) as SignUpPage2Fragment).setErrorMessages(
                        if (viewModel.birthday.value.isBlank()) "Please enter your birthday" else null,
                        if (viewModel.contactNumber.value.isBlank()) "Please enter your contact number"
                        else if (viewModel.contactNumber.value.length < 11) "Contact number must be in this format: 09XXXXXXXXX"
                        else null
                    )
                    binding.viewPagerSignUp.setCurrentItem(1, true)

                    return
                }

                if (position == 3 &&
                    (viewModel.email.value.isBlank() || viewModel.password.value.isBlank() ||
                     !AuthValidator.isValidEmail(viewModel.email.value) || !AuthValidator.isValidPassword(viewModel.password.value))
                ) {
                    (pagerAdapter.getFragment(3) as SignUpPage3Fragment).setErrorMessages(
                        if (viewModel.email.value.isBlank()) "Please enter your email address"
                        else if (!AuthValidator.isValidEmail(viewModel.email.value)) "Please enter a valid email address"
                        else null,
                        if (viewModel.password.value.isBlank()) "Please enter your password"
                        else if (!AuthValidator.isValidPassword(viewModel.password.value)) "Password must contain at least 8 characters"
                        else null
                    )
                    binding.viewPagerSignUp.setCurrentItem(2, true)

                    return
                }

                binding.signUpProgress.setProgress(position + 1, true)

                if (position == pagerAdapter.itemCount - 1) {
                    binding.btnSignUp.text = "Sign Up"
                } else {
                    binding.btnSignUp.text = "Next"
                }
            }
        })

        binding.btnSignUp.setOnClickListener {
            val currentPage = binding.viewPagerSignUp.currentItem

            when (currentPage) {
                0 -> {
                    if (viewModel.firstName.value.isBlank() || viewModel.lastName.value.isBlank()) {
                        (pagerAdapter.getFragment(0) as SignUpPage1Fragment).setErrorMessages(
                            if (viewModel.firstName.value.isBlank()) "Please enter your first name" else null,
                            if (viewModel.lastName.value.isBlank()) "Please enter your last name" else null
                        )
                        return@setOnClickListener
                    }
                }
                1 -> {
                    if (viewModel.birthday.value.isBlank() || viewModel.contactNumber.value.isBlank() ||
                        viewModel.contactNumber.value.length < 11
                    ) {
                        (pagerAdapter.getFragment(1) as SignUpPage2Fragment).setErrorMessages(
                            if (viewModel.birthday.value.isBlank()) "Please enter your birthday" else null,
                            if (viewModel.contactNumber.value.isBlank()) "Please enter your contact number"
                            else if (viewModel.contactNumber.value.length < 11) "Contact number must be in this format: 09XXXXXXXXX"
                            else null
                        )
                        return@setOnClickListener
                    }
                }
                2 -> {
                    if (viewModel.email.value.isBlank() || viewModel.password.value.isBlank() ||
                        !AuthValidator.isValidEmail(viewModel.email.value) || !AuthValidator.isValidPassword(viewModel.password.value)
                    ) {
                        (pagerAdapter.getFragment(2) as SignUpPage3Fragment).setErrorMessages(
                            if (viewModel.email.value.isBlank()) "Please enter your email address"
                            else if (!AuthValidator.isValidEmail(viewModel.email.value)) "Please enter a valid email address"
                            else null,
                            if (viewModel.password.value.isBlank()) "Please enter your password"
                            else if (!AuthValidator.isValidPassword(viewModel.password.value)) "Password must contain at least 8 characters"
                            else null
                        )
                        return@setOnClickListener
                    }
                }
            }

            if (currentPage < pagerAdapter.itemCount - 1) {
                binding.viewPagerSignUp.setCurrentItem(currentPage + 1, true)
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