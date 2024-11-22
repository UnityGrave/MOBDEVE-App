package com.mobdeve.senateelectioninfo.auth.model.service.impl

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.senateelectioninfo.auth.model.User
import com.mobdeve.senateelectioninfo.auth.model.service.AccountService
import com.mobdeve.senateelectioninfo.auth.model.service.UserProfileService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AccountServiceImpl : AccountService {

    private val firestore = FirebaseFirestore.getInstance()
    private val userProfileService: UserProfileService = UserProfileServiceImpl()

    companion object {
        private val instance: AccountService = AccountServiceImpl()

        fun getInstance(): AccountService {
            return this.instance
        }
    }

    override val currentUser: Flow<User?>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { user ->
                launch {
                if (user != null) {
                    val userProfile = userProfileService.getUserProfile(user.uid!!)
                    trySend(userProfile)
                } else {
                    trySend(null)
                }
                    }
            }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun logIn(email: String, password: String): Boolean {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            Log.e("AccountService", "Login error", e)
            false
        }
    }

    override suspend fun signUp(
        email: String, password: String,
        firstName: String, lastName: String,
        birthday: String, contactNumber: String): Boolean {
        return try {
            val signUpResult = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            val userId = signUpResult.user?.uid.orEmpty()
            val user = User(
                id = userId,
                email = email,
                firstName = firstName,
                lastName = lastName,
                birthday = birthday,
                contactNumber = contactNumber,
                profilePicture = "",
                authenticator = "disabled"
            )
            userProfileService.updateUserProfile(user)
            true
        } catch (e: Exception) {
            Log.e("AccountService", "Sign-up error", e)
            false
        }
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }

    override suspend fun updateProfile(user: User): Boolean {
        return userProfileService.updateUserProfile(user)
    }

    override suspend fun getProfile(): User? {
        val userId = currentUserId
        return userProfileService.getUserProfile(userId)
    }

    override suspend fun updateTwoFactorAuth(newStatus: String): Boolean {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return false

        return try {
            val userRef = firestore.collection("users").document(currentUserId)
            userRef.update("authenticator", newStatus).await()
            true
        } catch (e: Exception) {
            Log.e("AccountService", "Failed to update 2FA status", e)
            false
        }
    }
}