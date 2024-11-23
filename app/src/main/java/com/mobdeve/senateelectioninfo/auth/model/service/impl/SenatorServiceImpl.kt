package com.mobdeve.senateelectioninfo.auth.model.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.senateelectioninfo.auth.model.Senator
import kotlinx.coroutines.tasks.await

class SenatorServiceImpl {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getAllSenators(): ArrayList<Senator> {
        val senatorList = ArrayList<Senator>()

        try {
            val querySnapshot = firestore.collection("senators").get().await()
            for (document in querySnapshot.documents) {
                val senator = document.toObject(Senator::class.java)
                if (senator != null) {
                    senatorList.add(senator)
                }
            }
        } catch (exception: Exception) {
            throw exception
        }

        return senatorList
    }

    suspend fun getAllPartyLists(): List<String> {
        val partyLists = mutableListOf<String>()
        try {
            val result = firestore.collection("partyLists").get().await()
            for (document in result) {
                document.getString("name")?.let { partyLists.add(it) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return partyLists
    }
}