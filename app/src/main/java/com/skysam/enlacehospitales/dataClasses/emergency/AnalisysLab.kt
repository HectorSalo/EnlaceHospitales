package com.skysam.enlacehospitales.dataClasses.emergency

import java.util.Date

data class AnalisysLab(
    var date: Date,
    var hemoglobina: Double,
    var plaquetas: Double,
    var hematocrito: Double,
    var otros: String
)
