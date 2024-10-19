package com.example.mco2_interactiveprototype

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mco2_interactiveprototype.databinding.ActivityMainBinding
import com.example.mco2_interactiveprototype.databinding.ActivitySplashBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isLoggedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        isLoggedIn = this.intent.getBooleanExtra("LOGGED_IN", false)

        if (!isLoggedIn) {
            val i = Intent(applicationContext, SplashActivity::class.java)
            startActivity(i)
            return
        }

        // Replace with Home fragment on startup
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.profile -> replaceFragment(Profile())
                R.id.settings -> replaceFragment(Settings())
                R.id.ballot -> replaceFragment(Ballot())
                R.id.scanner -> replaceFragment(Scanner())
                else -> {}
            }
            true
        }
    }

    // Method to replace fragments
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}