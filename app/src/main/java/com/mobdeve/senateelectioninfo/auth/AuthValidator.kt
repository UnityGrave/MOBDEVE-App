package com.mobdeve.senateelectioninfo.auth

import android.util.Patterns

class AuthValidator {
    companion object {
        fun isValidContactNumber(contactNumber: String): Boolean {
            if (contactNumber.length < 11) return false
            else if (!contactNumber.startsWith("09")) return false
            else return true
        }

        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean {
            return password.length >= 8
        }
    }
}