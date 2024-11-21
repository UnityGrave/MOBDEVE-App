package com.mobdeve.senateelectioninfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.mobdeve.senateelectioninfo.auth.model.service.AccountService
import com.mobdeve.senateelectioninfo.auth.model.service.impl.AccountServiceImpl
import com.mobdeve.senateelectioninfo.databinding.ActivityMainBinding
import com.mobdeve.senateelectioninfo.splash.SplashModelViewFactory
import com.mobdeve.senateelectioninfo.splash.SplashViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel

    private lateinit var accountService: AccountService

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        accountService = AccountServiceImpl()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashViewModel = ViewModelProvider(this, SplashModelViewFactory(accountService, this.application)).get(SplashViewModel::class.java)
        val splashIntent = splashViewModel.checkLoggedInState()
        if (splashIntent != null) {
            startActivity(splashIntent)
            finish()
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.settings -> replaceFragment(SettingsFragment())
                R.id.ballot -> replaceFragment(BallotFragment())
                R.id.scanner -> replaceFragment(ScannerFragment())
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