package com.mobdeve.senateelectioninfo.auth.model.service.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.senateelectioninfo.auth.model.User
import com.mobdeve.senateelectioninfo.auth.model.service.UserProfileService
import kotlinx.coroutines.tasks.await

class UserProfileServiceImpl(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
): UserProfileService {
    override suspend fun getUserProfile(userId: String): User? {
        val userRef = firestore.collection("users").document(userId)
        val userDoc = userRef.get().await()
        return userDoc.toObject(User::class.java)
    }

    override suspend fun updateUserProfile(user: User): Boolean {
        val userRef = firestore.collection("users").document(user.id)
        try {
            userRef.set(user).await()
            return true
        } catch (e: Exception) {
            Log.e("UserProfileService", "Error updating user profile", e)
            return false
        }
    }
}