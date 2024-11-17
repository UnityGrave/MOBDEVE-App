package com.mobdeve.senateelectioninfo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.senateelectioninfo.databinding.ActivitySplashBinding

class SplashActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSplashSignUp.setOnClickListener {
            val i = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(i)
        }

        binding.btnSplashLogIn.setOnClickListener {
            val i = Intent(applicationContext, LogInActivity::class.java)
            startActivity(i)
        }
    }
}