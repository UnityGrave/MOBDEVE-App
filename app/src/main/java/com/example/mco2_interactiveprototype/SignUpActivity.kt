package com.example.mco2_interactiveprototype

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mco2_interactiveprototype.databinding.ActivitySignupBinding

class SignUpActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            i.putExtra("LOGGED_IN", true)
            startActivity(i)
        }

        binding.txtLoginLink.setOnClickListener {
            val i = Intent(applicationContext, LogInActivity::class.java)
            startActivity(i)
        }
    }
}