package com.skysam.enlacehospitales.dataClasses.emergency

data class Patient(
    var name: String,
    var gender: String,
    var age: Int,
    var phone: String,
    var comments: String,
    @field:JvmField
    var isBaptized: Boolean,
    @field:JvmField
    var isReputation: Boolean,
    @field:JvmField
    var isDpaComplete: Boolean,
    var congregation: String,
    var childPatient: ChildPatient?
)
