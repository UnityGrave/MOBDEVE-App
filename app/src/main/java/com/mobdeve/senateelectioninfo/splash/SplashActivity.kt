package com.mobdeve.senateelectioninfo.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.databinding.ActivitySplashBinding

class SplashActivity: AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = SplashModelViewFactory(AccountServiceImpl.getInstance(), application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSplashSignUp.setOnClickListener {
            viewModel.onSignUpClicked(this)
        }

        binding.btnSplashLogIn.setOnClickListener {
            viewModel.onLogInClicked(this)
        }
    }
}