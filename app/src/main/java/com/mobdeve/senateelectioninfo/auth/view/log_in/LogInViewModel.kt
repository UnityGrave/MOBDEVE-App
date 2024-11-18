package com.mobdeve.senateelectioninfo.auth.view.log_in

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobdeve.senateelectioninfo.auth.AuthValidator
import com.mobdeve.senateelectioninfo.auth.model.service.AccountService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LogInViewModel(
    private val accountService: AccountService,
    private val application: Application
): AndroidViewModel(Application()) {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun updateEmail(email: String) {
        this.email.value = email
    }

    fun updatePassword(password: String) {
        this.password.value = password
    }

    fun onLogInClick(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (!AuthValidator.isValidEmail(email.value) || !AuthValidator.isValidPassword(password.value)) {
                return@launch
            }

            val task = accountService.logIn(email.value, password.value)

            callback(task)
        }
    }
}