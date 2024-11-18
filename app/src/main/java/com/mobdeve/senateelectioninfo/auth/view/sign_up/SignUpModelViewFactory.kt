package com.mobdeve.senateelectioninfo.auth.view.sign_up

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.senateelectioninfo.auth.model.service.AccountService

class SignUpViewModelFactory(private val accountService: AccountService, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(accountService, application) as T
    }
}