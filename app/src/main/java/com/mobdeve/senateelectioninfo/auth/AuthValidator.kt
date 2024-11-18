package com.mobdeve.senateelectioninfo.auth

import android.util.Patterns

class AuthValidator {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            return password.length >= 8
        }
    }
}