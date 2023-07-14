package com.skysam.enlacehospitales.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.skysam.enlacehospitales.common.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


object Auth {

    private fun getInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
    fun getCurrentUser(): FirebaseUser? {
        return getInstance().currentUser
    }

    fun initSession(email: String, password: String): Flow<String?> {
        return callbackFlow {
            getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    trySend(Constants.RESULT_OK)
                }
                .addOnFailureListener {
                    trySend(it.localizedMessage)
                }
            awaitClose {  }
        }
    }
}