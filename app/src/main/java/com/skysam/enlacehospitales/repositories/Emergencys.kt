package com.skysam.enlacehospitales.repositories

import android.content.ContentValues
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab
import com.skysam.enlacehospitales.dataClasses.emergency.ArticlesMedical
import com.skysam.enlacehospitales.dataClasses.emergency.BornPatient
import com.skysam.enlacehospitales.dataClasses.emergency.ChildPatient
import com.skysam.enlacehospitales.dataClasses.emergency.Doctor
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.dataClasses.emergency.Tracing
import com.skysam.enlacehospitales.dataClasses.emergency.TransferPatient
import com.skysam.enlacehospitales.dataClasses.emergency.Tratment
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

                    val emergencys = mutableListOf<Emergency>()
                    for (emergency in value!!) {
                        val itemNotif = emergency.data.getValue(Constants.NOTIFICATION) as HashMap<*, *>
                        val timestampNotif: Timestamp = itemNotif[Constants.DATE_CALL] as Timestamp
                        val dateNotif = timestampNotif.toDate()

                        val notification = Notification(
                            dateNotif,
                            itemNotif[Constants.PERSON_CALL].toString(),
                            itemNotif[Constants.RELATIONSHIP_PATIENT].toString(),
                            itemNotif[Constants.INFO_PERSON_CALL].toString(),
                            itemNotif[Constants.IS_NEED_HELP].toString().toBoolean()
                        )

                        val item = emergency.data.getValue(Constants.PATIENT) as HashMap<*, *>
                        /*@Suppress("UNCHECKED_CAST")
                        val olders = emergency.data.getValue(Constants.NAMES_OLDERS_CONTACTED) as List<String>
                        @Suppress("UNCHECKED_CAST")
                        val phones = emergency.data.getValue(Constants.PHONES_OLDERS_CONTACTED) as List<String>*/

                        val childPatient = if (item[Constants.CHILD_PATIENT] != null) {
                            val bornPatient = if(item[Constants.BORN_PATIENT] != null) {
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
                                bornPatient
                            )
                        } else null

                        val patient = Patient(
                            item[Constants.NAME].toString(),
                            item[Constants.GENDER].toString(),
                            item[Constants.AGE].toString().toInt(),
                            item[Constants.PHONE].toString(),
                            item[Constants.COMMENTS].toString(),
                            item[Constants.IS_BAPTIZED].toString().toBoolean(),
                            item[Constants.IS_REPUTATION].toString().toBoolean(),
                            item[Constants.IS_DPA].toString().toBoolean(),
                            item[Constants.CONGREGATION].toString(),
                            childPatient
                        )

                        val itemHospital = emergency.data.getValue(Constants.HOSPITAL) as HashMap<*, *>
                        val hospital = Hospital(
                            itemHospital[Constants.NAME_HOSPITAL].toString(),
                            itemHospital[Constants.ROOM].toString(),
                            itemHospital[Constants.NAMES_OLDERS_CONTACTED].toString(),
                            itemHospital[Constants.PHONES_OLDERS_CONTACTED].toString(),
                        )

                        val listLab = mutableListOf<AnalisysLab>()
                        if (emergency.get(Constants.ANALISYS_LAB) != null) {
                            @Suppress("UNCHECKED_CAST")
                            val list = emergency.data.getValue(Constants.ANALISYS_LAB) as List<HashMap<String, Any>>
                            for (itemLab in list) {
                                val timestamp: Timestamp = item[Constants.DATE] as Timestamp
                                val date = timestamp.toDate()

                                val analisysLab = AnalisysLab(
                                    date,
                                    itemLab[Constants.HEMOGLOBINA].toString().toDouble(),
                                    itemLab[Constants.PLAQUETAS].toString().toDouble(),
                                    itemLab[Constants.HEMATOCRITO].toString().toDouble(),
                                    itemLab[Constants.OTHERS].toString()
                                )
                                listLab.add(analisysLab)
                            }
                        }

                        val listDoctors = mutableListOf<Doctor>()
                        if (emergency.get(Constants.DOCTORS) != null) {
                            @Suppress("UNCHECKED_CAST")
                            val list = emergency.data.getValue(Constants.DOCTORS) as List<HashMap<String, Any>>
                            for (itemLab in list) {
                                val doctor = Doctor(
                                    itemLab[Constants.NAME].toString(),
                                    itemLab[Constants.SPECIALITY].toString(),
                                    itemLab[Constants.METHOD_CONTACT].toString(),
                                    itemLab[Constants.INFORMATION].toString()
                                )
                                listDoctors.add(doctor)
                            }
                        }

                        val tratment = if (emergency.get(Constants.TRATMENT) != null) {
                            @Suppress("UNCHECKED_CAST")
                            val trat = emergency.data.getValue(Constants.TRATMENT) as HashMap<String, Any>
                            Tratment(
                                trat[Constants.INFORMATION].toString(),
                                trat[Constants.IS_COMMUNICATED_WITH_DOCTORS].toString().toBoolean()
                            )
                        } else null

                        val strategies = if (emergency.getString(Constants.STRATEGIES) != null)
                            emergency.getString(Constants.STRATEGIES)!! else ""

                        val articlesMedical = if (emergency.get(Constants.ARTICLES_MEDICAL) != null) {
                            @Suppress("UNCHECKED_CAST")
                            val article = emergency.data.getValue(Constants.ARTICLES_MEDICAL) as HashMap<String, Any>
                            ArticlesMedical(
                                article[Constants.ARTICLES].toString(),
                                article[Constants.IS_DOCTOR_COLABORATED].toString().toBoolean()
                            )
                        } else null

                        val isTalkWithSecondDoctor = if (emergency.getBoolean(Constants.IS_TALK_WITH_SECOND_DOCTOR) != null)
                            emergency.getBoolean(Constants.IS_TALK_WITH_SECOND_DOCTOR)!! else false

                        val secondDoctor = if (emergency.get(Constants.SECOND_DOCTOR) != null) {
                            @Suppress("UNCHECKED_CAST")
                            val itemSecondDoctor = emergency.data.getValue(Constants.SECOND_DOCTOR) as HashMap<String, Any>
                            Doctor(
                                itemSecondDoctor[Constants.NAME].toString(),
                                itemSecondDoctor[Constants.SPECIALITY].toString(),
                                itemSecondDoctor[Constants.METHOD_CONTACT].toString(),
                                itemSecondDoctor[Constants.INFORMATION].toString()
                            )
                        } else null

                        val isTransfered = if (emergency.getBoolean(Constants.IS_TRANSFERED) != null)
                            emergency.getBoolean(Constants.IS_TRANSFERED)!! else false

                        val transferPatient = if (emergency.get(Constants.TRANSFER_PATIENT) != null) {
                            @Suppress("UNCHECKED_CAST")
                            val transfer = emergency.data.getValue(Constants.TRANSFER_PATIENT) as HashMap<String, Any>
                            TransferPatient(
                                transfer[Constants.IS_PLANS_CONFIRMED].toString().toBoolean(),
                                transfer[Constants.IS_CONTACTED_INFORMATION_HOSPITALS].toString().toBoolean(),
                                transfer[Constants.NAME_HOSPITAL].toString(),
                                transfer[Constants.NAME_DOCTOR].toString(),
                                transfer[Constants.PHONE_HOSPITAL].toString(),
                                transfer[Constants.INFORMATION].toString()
                            )
                        } else null

                        val tracing = if (emergency.get(Constants.TRACING) != null) {
                            @Suppress("UNCHECKED_CAST")
                            val article = emergency.data.getValue(Constants.TRACING) as HashMap<String, Any>
                            Tracing(
                                article[Constants.IS_CONTACTED_OLDERS_LOCALS].toString().toBoolean(),
                                article[Constants.RESULTS].toString()
                            )
                        } else null

                        val emergencyNew = Emergency(
                            emergency.id,
                            emergency.getDate(Constants.DATE)!!,
                            emergency.getDate(Constants.DATE_UPDATED)!!,
                            emergency.getString(Constants.STATUS)!!,
                            emergency.getString(Constants.SPECIALITY)!!,
                            notification,
                            patient,
                            hospital,
                            emergency.getString(Constants.ISSUE_MEDICAL)!!,
                            listLab,
                            listDoctors,
                            tratment,
                            strategies,
                            articlesMedical,
                            isTalkWithSecondDoctor,
                            secondDoctor,
                            isTransfered,
                            transferPatient,
                            tracing
                        )
                        emergencys.add(emergencyNew)
                    }
                    trySend(emergencys)
                }
            awaitClose { request.remove() }
        }
    }

    fun saveEmergency(emergency: Emergency) {
        val data = hashMapOf(
            Constants.DATE to emergency.dateCreated,
            Constants.DATE_UPDATED to emergency.dateUdpdated,
            Constants.STATUS to emergency.status,
            Constants.NOTIFICATION to emergency.notification,
            Constants.PATIENT to emergency.patient,
            Constants.HOSPITAL to emergency.hospital,
            Constants.ISSUE_MEDICAL to emergency.issueMedical
        )
        getInstance().add(data)
    }

    fun finishEmergency(emergency: Emergency) {
        getInstance()
            .document(emergency.id)
            .update(Constants.PATIENT, null, Constants.STATUS, Constants.IS_ACTIVE)
    }
}