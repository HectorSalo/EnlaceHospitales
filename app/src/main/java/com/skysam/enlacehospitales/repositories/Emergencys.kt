package com.skysam.enlacehospitales.repositories

import android.content.ContentValues
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
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
import com.skysam.enlacehospitales.dataClasses.emergency.Treatment
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
                .addSnapshotListener (MetadataChanges.INCLUDE){ value, error ->
                    if (error != null) {
                        Log.w(ContentValues.TAG, "Listen failed.", error)
                        return@addSnapshotListener
                    }

                    val emergencys = mutableListOf<Emergency>()
                    for (emergency in value!!) {

                        val notification = if (emergency.get(Constants.NOTIFICATION) != null) {
                            val itemNotif =
                                emergency.data.getValue(Constants.NOTIFICATION) as HashMap<*, *>
                            val timestampNotif: Timestamp =
                                itemNotif[Constants.DATE_CALL] as Timestamp
                            val dateNotif = timestampNotif.toDate()

                            Notification(
                                dateNotif,
                                itemNotif[Constants.PERSON_CALL].toString(),
                                itemNotif[Constants.RELATIONSHIP_PATIENT].toString(),
                                itemNotif[Constants.INFO_PERSON_CALL].toString(),
                                itemNotif[Constants.IS_NEED_HELP].toString().toBoolean()
                            )
                        } else null

                        val patient = if (emergency.get(Constants.PATIENT) != null) {
                            val itemPatient = emergency.data.getValue(Constants.PATIENT) as HashMap<*, *>

                            val childPatient = if (itemPatient[Constants.CHILD_PATIENT] != null) {
                                val bornPatient = if(itemPatient[Constants.BORN_PATIENT] != null) {
                                    val timestamp: Timestamp = itemPatient[Constants.DATE_BORN] as Timestamp
                                    val date = timestamp.toDate()
                                    BornPatient(
                                        itemPatient[Constants.WEIGHT].toString().toDouble(),
                                        itemPatient[Constants.WEEKS_AGE].toString().toInt(),
                                        date,
                                        itemPatient[Constants.BORN_APGAR].toString().toDouble(),
                                        itemPatient[Constants.FIVE_MINUTES_APGAR].toString().toDouble(),
                                    )
                                } else null

                                ChildPatient(
                                    itemPatient[Constants.NAME_FATHER].toString(),
                                    itemPatient[Constants.NAME_MOTHER].toString(),
                                    itemPatient[Constants.IS_FATHER_BAPTIZED].toString().toBoolean(),
                                    itemPatient[Constants.IS_MOTHER_BAPTIZED].toString().toBoolean(),
                                    itemPatient[Constants.COMMENTS].toString(),
                                    bornPatient
                                )
                            } else null

                            Patient(
                                itemPatient[Constants.NAME].toString(),
                                itemPatient[Constants.GENDER].toString(),
                                itemPatient[Constants.AGE].toString().toInt(),
                                itemPatient[Constants.PHONE].toString(),
                                itemPatient[Constants.COMMENTS].toString(),
                                itemPatient[Constants.IS_BAPTIZED].toString().toBoolean(),
                                itemPatient[Constants.IS_REPUTATION].toString().toBoolean(),
                                itemPatient[Constants.IS_DPA].toString().toBoolean(),
                                itemPatient[Constants.CONGREGATION].toString(),
                                childPatient
                            )
                        } else null

                        val hospital = if (emergency.data.getValue(Constants.HOSPITAL) != null) {
                            val itemHospital = emergency.data.getValue(Constants.HOSPITAL) as HashMap<*, *>
                            Hospital(
                                itemHospital[Constants.NAME_HOSPITAL].toString(),
                                itemHospital[Constants.ROOM].toString(),
                                itemHospital[Constants.NAMES_OLDERS_CONTACTED].toString(),
                                itemHospital[Constants.PHONES_OLDERS_CONTACTED].toString(),
                            )
                        } else null

                        val listLab = mutableListOf<AnalisysLab>()
                        if (emergency.get(Constants.ANALISYS_LAB) != null) {
                            @Suppress("UNCHECKED_CAST")
                            val list = emergency.data.getValue(Constants.ANALISYS_LAB) as List<HashMap<String, Any>>
                            for (itemLab in list) {
                                val timestamp: Timestamp = itemLab[Constants.DATE] as Timestamp
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

                        val treatment = if (emergency.get(Constants.TRATMENT) != null) {
                            @Suppress("UNCHECKED_CAST")
                            val trat = emergency.data.getValue(Constants.TRATMENT) as HashMap<String, Any>
                            Treatment(
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
                            emergency.getBoolean(Constants.STATUS)!!,
                            emergency.getString(Constants.SPECIALITY)!!,
                            notification,
                            patient,
                            hospital,
                            emergency.getString(Constants.ISSUE_MEDICAL)!!,
                            listLab.sortedBy { it.date },
                            listDoctors,
                            treatment,
                            strategies,
                            articlesMedical,
                            secondDoctor,
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
            Constants.SPECIALITY to emergency.speciality,
            Constants.NOTIFICATION to emergency.notification,
            Constants.PATIENT to emergency.patient,
            Constants.HOSPITAL to emergency.hospital,
            Constants.ISSUE_MEDICAL to emergency.issueMedical,
            Constants.ANALISYS_LAB to emergency.analisysLab,
            Constants.DOCTORS to emergency.doctors,
            Constants.STRATEGIES to emergency.strategies,
            Constants.ARTICLES_MEDICAL to emergency.articlesMedical,
            Constants.SECOND_DOCTOR to emergency.secondDoctor,
            Constants.TRANSFER_PATIENT to emergency.transferPatient,
            Constants.TRACING to emergency.tracing
        )
        getInstance().add(data)
    }

    fun saveNewLab(id: String, lab: AnalisysLab) {
        getInstance()
            .document(id)
            .update(Constants.ANALISYS_LAB, FieldValue.arrayUnion(lab))
    }

    fun setSpeciality(id: String, speciality: String) {
        getInstance()
            .document(id)
            .update(Constants.SPECIALITY, speciality)
    }

    fun finishEmergency(emergency: Emergency) {
        val data: Map<String, Any?> = hashMapOf(
            Constants.PATIENT to null,
            Constants.NOTIFICATION to null,
            Constants.HOSPITAL to null,
            Constants.STATUS to false
        )
        getInstance()
            .document(emergency.id)
            .update(data)
    }
}