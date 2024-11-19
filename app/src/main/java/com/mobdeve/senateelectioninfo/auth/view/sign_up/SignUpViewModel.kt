package com.mobdeve.senateelectioninfo.auth.view.sign_up

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobdeve.senateelectioninfo.auth.AuthValidator
import com.mobdeve.senateelectioninfo.auth.model.service.AccountService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val accountService: AccountService,
    private val application: Application
): AndroidViewModel(application) {

    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")
    val birthday = MutableStateFlow("")
    val contactNumber = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun updateFirstName(firstName: String) {
        this.firstName.value = firstName
    }

    fun updateLastName(lastName: String) {
        this.lastName.value = lastName
    }

    fun updateBirthday(birthday: String) {
        this.birthday.value = birthday
    }

    fun updateContactNumber(contactNumber: String) {
        this.contactNumber.value = contactNumber
    }

    fun updateEmail(email: String) {
        this.email.value = email
    }

    fun updatePassword(password: String) {
        this.password.value = password
    }

    fun onSignUpClick(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (firstName.value.isBlank() || lastName.value.isBlank() || !AuthValidator.isValidEmail(email.value) || !AuthValidator.isValidPassword(password.value)) {
                Toast.makeText(getApplication(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val task = accountService.signUp(
                email.value, password.value,
                firstName.value, lastName.value,
                birthday.value, contactNumber.value
            )

            callback(task)
        }
    }
}