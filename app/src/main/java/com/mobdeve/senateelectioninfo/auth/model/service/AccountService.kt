package com.mobdeve.senateelectioninfo.auth.model.service

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.mobdeve.senateelectioninfo.auth.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun logIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun signOut()
    suspend fun deleteAccount()
}