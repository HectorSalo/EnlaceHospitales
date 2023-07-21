package com.skysam.enlacehospitales.dataClasses.emergency

data class TransferPatient(
    var isPlansConfirmed: Boolean,
    var isContactedInformationHospitals: Boolean,
    var nameHospital: String,
    var nameDoctor: String,
    var phoneHospital: String,
    var information: String
)
