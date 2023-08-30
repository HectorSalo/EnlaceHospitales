package com.skysam.enlacehospitales.dataClasses.emergency

data class TransferPatient(
    @field:JvmField
    var isPlansConfirmed: Boolean,
    @field:JvmField
    var isContactedInformationHospitals: Boolean,
    var nameHospital: String,
    var nameDoctor: String,
    var phoneHospital: String,
    var information: String
)
