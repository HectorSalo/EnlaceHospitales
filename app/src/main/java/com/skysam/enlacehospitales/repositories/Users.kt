package com.skysam.enlacehospitales.repositories

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.Utils

object Users {
    private val PATH_USERS = when(Utils.getEnviroment()) {
        Constants.DEMO -> Constants.USERS_DEMO
        Constants.RELEASE -> Constants.USERS
        else -> Constants.USERS
    }

    private fun getInstance(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(PATH_USERS)
    }


}