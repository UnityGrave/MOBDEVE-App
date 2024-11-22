package com.mobdeve.senateelectioninfo.auth.model

data class User (
    val id: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val birthday: String = "",
    val contactNumber: String = "",
    val profilePicture: String = "",
    val authenticator: String = ""
)