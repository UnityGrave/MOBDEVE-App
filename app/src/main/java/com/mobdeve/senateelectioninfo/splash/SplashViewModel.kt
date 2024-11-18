package com.mobdeve.senateelectioninfo.splash

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.mobdeve.senateelectioninfo.auth.model.service.AccountService
import com.mobdeve.senateelectioninfo.auth.view.log_in.LogInActivity
import com.mobdeve.senateelectioninfo.auth.view.sign_up.SignUpActivity

class SplashViewModel(
    private val accountService: AccountService,
    private val application: Application
): AndroidViewModel(application) {

    fun onSignUpClicked(activity: SplashActivity) {
        val i = Intent(getApplication(), SignUpActivity::class.java)
        activity.startActivity(i)
        activity.finish()
    }

    fun onLogInClicked(activity: SplashActivity) {
        val i = Intent(getApplication(), LogInActivity::class.java)
        activity.startActivity(i)
        activity.finish()
    }

    fun checkLoggedInState(): Intent? {
        return if (accountService.hasUser()) {
            null
        } else {
            Intent(getApplication(), SplashActivity::class.java)
        }
    }
}