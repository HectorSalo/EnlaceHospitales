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

object MembersHlc {
    private val PATH = when(Utils.getEnviroment()) {
        Constants.DEMO -> Constants.MEMBERS_HLC_DEMO
        Constants.RELEASE -> Constants.MEMBERS_HLC
        else -> Constants.MEMBERS_HLC
    }

    private fun getInstance(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(PATH)
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
                        var list: List<Int>
                        @Suppress("UNCHECKED_CAST")
                        list = doc.data.getValue(Constants.GUARD) as List<Int>

                        val member = Member(
                            doc.id,
                            doc.getString(Constants.NAME)!!,
                            doc.getString(Constants.EMAIL)!!,
                            doc.getString(Constants.PASSWORD)!!,
                            doc.getString(Constants.CONGREGATION)!!,
                            doc.getString(Constants.PHONE)!!,
                            doc.getString(Constants.ROLE)!!,
                            doc.getDate(Constants.DATE)!!,
                            doc.getString(Constants.SPECIALITY)!!,
                            list
                        )
                        members.add(member)
                    }
                    trySend(Utils.organizedAlphabeticListMembers(members))
                }
            awaitClose { request.remove() }
        }
    }

    fun saveMember(member: Member) {
        val data = hashMapOf(
            Constants.NAME to member.name,
            Constants.EMAIL to member.email,
            Constants.PASSWORD to member.password,
            Constants.CONGREGATION to member.congregation,
            Constants.PHONE to member.phone,
            Constants.ROLE to member.role,
            Constants.DATE to member.dateCreated,
            Constants.SPECIALITY to member.speciality,
            Constants.GUARD to member.guard
        )
        getInstance().add(data)
    }

    fun updateMember(member: Member) {
        val data = hashMapOf(
            Constants.NAME to member.name,
            Constants.EMAIL to member.email,
            Constants.PASSWORD to member.password,
            Constants.CONGREGATION to member.congregation,
            Constants.PHONE to member.phone,
            Constants.ROLE to member.role,
            Constants.DATE to member.dateCreated,
            Constants.SPECIALITY to member.speciality,
            Constants.GUARD to member.guard
        )
        getInstance()
            .document(member.id)
            .update(data)
    }

    fun deleteMember(id: String) {
        getInstance()
            .document(id)
            .delete()
    }
}