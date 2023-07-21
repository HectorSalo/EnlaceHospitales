package com.skysam.enlacehospitales.dataClasses.emergency

data class ChildPatient(
    var nameFather: String,
    var nameMother: String,
    var isFatherBaptized: Boolean,
    var isMotherBaptized: Boolean,
    var comments: String,
    var isBorn: Boolean,
    var bornPatient: BornPatient
)
