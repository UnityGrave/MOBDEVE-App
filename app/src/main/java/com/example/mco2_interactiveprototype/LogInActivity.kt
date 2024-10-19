package com.example.mco2_interactiveprototype

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mco2_interactiveprototype.databinding.ActivityLoginBinding

class LogInActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLogIn.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            i.putExtra("LOGGED_IN", true)
            startActivity(i)
        }

        binding.txtSignUpLink.setOnClickListener {
            val i = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(i)
        }
    }
}