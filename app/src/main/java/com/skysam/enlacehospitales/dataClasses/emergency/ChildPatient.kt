package com.skysam.enlacehospitales.dataClasses.emergency

data class ChildPatient(
    var nameFather: String,
    var nameMother: String,
    @field:JvmField
    var isFatherBaptized: Boolean,
    @field:JvmField
    var isMotherBaptized: Boolean,
    var comments: String,
    var bornPatient: BornPatient?
)
