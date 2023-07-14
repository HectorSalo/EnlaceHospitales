package com.skysam.enlacehospitales.repositories

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object UsersRepository {
    private val PATH_USERS = when(Utils.getEnviroment()) {
        Constants.DEMO -> Constants.USERS_DEMO
        Constants.RELEASE -> Constants.USERS
        else -> Constants.USERS
    }

    private fun getInstance(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(PATH_USERS)
    }

    fun getUsers(): Flow<List<User>> {
        return callbackFlow {
            val request = getInstance()
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.w(ContentValues.TAG, "Listen failed.", error)
                        return@addSnapshotListener
                    }

                    val users = mutableListOf<User>()
                    for (doc in value!!) {
                        val user = User(
                            doc.id,
                            doc.getString(Constants.NAME)!!,
                            doc.getString(Constants.EMAIL)!!,
                            doc.getString(Constants.PASSWORD)!!,
                            doc.getString(Constants.CONGREGATION)!!,
                            doc.getString(Constants.PHONE)!!,
                            doc.getString(Constants.ROLE)!!
                        )
                        users.add(user)
                    }
                    trySend(users)
                }
            awaitClose { request.remove() }
        }
    }
}