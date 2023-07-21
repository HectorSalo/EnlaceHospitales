package com.skysam.enlacehospitales.dataClasses.emergency

import java.util.Date

data class BornPatient(
    var weight: Double,
    var weeksAge: Int,
    var dateBorn: Date,
    var bornAPGAR: Double,
    var fiveMinutesAPGAR: Double
)
