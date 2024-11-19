package com.mobdeve.senateelectioninfo.auth.model.service

import com.mobdeve.senateelectioninfo.auth.model.User

interface UserProfileService {
    suspend fun getUserProfile(userId: String): User?
    suspend fun updateUserProfile(user: User): Boolean
}