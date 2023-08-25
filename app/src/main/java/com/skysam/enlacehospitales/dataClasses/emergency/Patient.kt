package com.skysam.enlacehospitales.dataClasses.emergency

data class Patient(
    var name: String,
    var gender: String,
    var age: Int,
    var phone: String,
    var comments: String,
    var isBaptized: Boolean,
    var isReputation: Boolean,
    var isDpaComplete: Boolean,
    var congregation: String,
    var childPatient: ChildPatient?
)
