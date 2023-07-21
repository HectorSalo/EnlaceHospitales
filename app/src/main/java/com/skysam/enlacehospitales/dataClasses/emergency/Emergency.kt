package com.skysam.enlacehospitales.dataClasses.emergency

data class Emergency(
    val id: String,
    val notification: Notification,
    val patient: Patient,
    var issueMedical: String,
    var analisysLab: MutableList<AnalisysLab>,
    var doctors: MutableList<Doctor>,
    var tratment: Tratment,
    var strategies: String,
    var articlesMedical: ArticlesMedical,
    var isTalkWithSecondDoctor: Boolean,
    var secondDoctor: Doctor,
    var isTransfered: Boolean,
    var transferPatient: TransferPatient,
    var tracing: Tracing
)
