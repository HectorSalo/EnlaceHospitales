package com.skysam.enlacehospitales.dataClasses.emergency

data class Treatment(
    var information: String,
    @field:JvmField
    var isCommunicatedWithDoctors: Boolean
)
