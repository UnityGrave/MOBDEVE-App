package com.mobdeve.senateelectioninfo.auth.view.log_in

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.senateelectioninfo.auth.model.service.AccountService

class LogInViewModelFactory(private val accountService: AccountService, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LogInViewModel(accountService, application) as T
    }
}