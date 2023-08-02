package com.skysam.enlacehospitales.repositories

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.Member
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object Members {
    private val PATH_USERS = when(Utils.getEnviroment()) {
        Constants.DEMO -> Constants.MEMBERS_DEMO
        Constants.RELEASE -> Constants.MEMBERS
        else -> Constants.MEMBERS
    }

    private fun getInstance(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(PATH_USERS)
    }

    fun getMembers(): Flow<List<Member>> {
        return callbackFlow {
            val request = getInstance()
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.w(ContentValues.TAG, "Listen failed.", error)
                        return@addSnapshotListener
                    }

                    val members = mutableListOf<Member>()
                    for (doc in value!!) {
                        val member = Member(
                            doc.id,
                            doc.getString(Constants.NAME)!!,
                            doc.getString(Constants.EMAIL)!!,
                            doc.getString(Constants.PASSWORD)!!,
                            doc.getString(Constants.CONGREGATION)!!,
                            doc.getString(Constants.PHONE)!!,
                            doc.getString(Constants.ROLE)!!,
                            doc.getDate(Constants.DATE)!!,
                            doc.getString(Constants.STATUS)!!,
                            doc.getBoolean(Constants.GUARD)!!
                        )
                        members.add(member)
                    }
                    trySend(members)
                }
            awaitClose { request.remove() }
        }
    }
}