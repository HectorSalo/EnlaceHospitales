package com.skysam.enlacehospitales.dataClasses.emergency

import java.util.Date

data class Emergency(
    val id: String,
    val dateCreated: Date,
    val dateUdpdated: Date,
    var status: Boolean,
    var speciality: String,
    val notification: Notification?,
    var patient: Patient?,
    val hospital: Hospital?,
    var issueMedical: String,
    var analisysLab: List<AnalisysLab>,
    var doctors: List<Doctor>,
    var treatment: Treatment?,
    var strategies: String,
    var articlesMedical: ArticlesMedical?,
    var secondDoctor: Doctor?,
    var transferPatient: TransferPatient?,
    var tracing: Tracing?
)
