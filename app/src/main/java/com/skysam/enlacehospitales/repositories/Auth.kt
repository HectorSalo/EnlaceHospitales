package com.skysam.enlacehospitales.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object Auth {
    fun getCurrentUser(): FirebaseUser? {
        val mAuth = FirebaseAuth.getInstance()
        return mAuth.currentUser
    }
}