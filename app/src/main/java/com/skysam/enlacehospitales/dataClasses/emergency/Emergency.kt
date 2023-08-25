package com.skysam.enlacehospitales.dataClasses.emergency

import java.util.Date

data class Emergency(
    val id: String,
    val dateCreated: Date,
    val dateUdpdated: Date,
    var status: String,
    var speciality: String,
    val notification: Notification,
    var patient: Patient?,
    val hospital: Hospital,
    var issueMedical: String,
    var analisysLab: List<AnalisysLab>,
    var doctors: List<Doctor>,
    var tratment: Tratment?,
    var strategies: String,
    var articlesMedical: ArticlesMedical?,
    var isTalkWithSecondDoctor: Boolean,
    var secondDoctor: Doctor?,
    var isTransfered: Boolean,
    var transferPatient: TransferPatient?,
    var tracing: Tracing?
)
