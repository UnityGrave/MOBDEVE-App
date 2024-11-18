package com.mobdeve.senateelectioninfo.splash

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.senateelectioninfo.auth.model.service.AccountService

class SplashModelViewFactory(private val accountService: AccountService, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(accountService, application) as T
    }
}