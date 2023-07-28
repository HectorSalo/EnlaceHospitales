package com.skysam.enlacehospitales.repositories

import android.content.ContentValues
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.BornPatient
import com.skysam.enlacehospitales.dataClasses.emergency.ChildPatient
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object Emergencys {
    private val PATH = when(Utils.getEnviroment()) {
        Constants.DEMO -> Constants.EMERGENCYS_DEMO
        Constants.RELEASE -> Constants.EMERGENCYS
        else -> Constants.EMERGENCYS
    }

    private fun getInstance(): CollectionReference {
        return FirebaseFirestore.getInstance().collection(PATH)
    }

    fun getEmergencys(): Flow<List<Emergency>> {
        return callbackFlow {
            val request = getInstance()
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.w(ContentValues.TAG, "Listen failed.", error)
                        return@addSnapshotListener
                    }

                    val emergencys = listOf<Emergency>()
                    for (emergency in value!!) {
                        if (emergency.get(Constants.NOTIFICATION) != null) {
                            val item = emergency.data.getValue(Constants.NOTIFICATION) as HashMap<*, *>
                            val timestamp: Timestamp = item[Constants.DATE] as Timestamp
                            val date = timestamp.toDate()

                            val notification = Notification(
                                date,
                                item[Constants.PERSON_CALL].toString(),
                                item[Constants.RELATIONSHIP_PATIENT].toString(),
                                item[Constants.INFO_PERSON_CALL].toString(),
                                item[Constants.IS_NEED_HELP].toString().toBoolean()
                            )
                        }

                        if (emergency.get(Constants.PATIENT) != null) {
                            val item = emergency.data.getValue(Constants.PATIENT) as HashMap<*, *>
                            @Suppress("UNCHECKED_CAST")
                            val olders = emergency.data.getValue(Constants.NAMES_OLDERS_CONTACTED) as List<String>
                            @Suppress("UNCHECKED_CAST")
                            val phones = emergency.data.getValue(Constants.PHONES_OLDERS_CONTACTED) as List<String>

                            val childPatient = if (item[Constants.IS_CHILD].toString().toBoolean()) {
                                val bornPatient = if(item[Constants.IS_BORN].toString().toBoolean()) {
                                    val timestamp: Timestamp = item[Constants.DATE_BORN] as Timestamp
                                    val date = timestamp.toDate()
                                    BornPatient(
                                        item[Constants.WEIGHT].toString().toDouble(),
                                        item[Constants.WEEKS_AGE].toString().toInt(),
                                        date,
                                        item[Constants.BORN_APGAR].toString().toDouble(),
                                        item[Constants.FIVE_MINUTES_APGAR].toString().toDouble(),
                                    )
                                } else null

                                ChildPatient(
                                    item[Constants.NAME_FATHER].toString(),
                                    item[Constants.NAME_MOTHER].toString(),
                                    item[Constants.IS_FATHER_BAPTIZED].toString().toBoolean(),
                                    item[Constants.IS_MOTHER_BAPTIZED].toString().toBoolean(),
                                    item[Constants.COMMENTS].toString(),
                                    item[Constants.IS_BORN].toString().toBoolean(),
                                    bornPatient
                                )
                            } else null

                            val patient = Patient(
                                item[Constants.NAME].toString(),
                                item[Constants.GENDER].toString(),
                                item[Constants.AGE].toString().toInt(),
                                item[Constants.COMMENTS].toString(),
                                item[Constants.IS_BAPTIZED].toString().toBoolean(),
                                item[Constants.IS_REPUTATION].toString().toBoolean(),
                                item[Constants.NAME_HOSPITAL].toString(),
                                item[Constants.ROOM].toString(),
                                item[Constants.PHONE].toString(),
                                item[Constants.CONGREGATION].toString(),
                                olders,
                                phones,
                                item[Constants.IS_CHILD].toString().toBoolean(),
                                childPatient
                            )
                        }
                    }
                }
            awaitClose { request.remove() }
        }
    }
}