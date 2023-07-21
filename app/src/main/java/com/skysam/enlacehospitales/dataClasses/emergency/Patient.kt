package com.skysam.enlacehospitales.dataClasses.emergency

data class Patient(
    var name: String,
    var gender: String,
    var age: Int,
    var comments: String,
    var isBaptized: Boolean,
    var isReputation: Boolean,
    var nameHospital: String,
    var room: String,
    var phone: String,
    var congregation: String,
    var namesOldersContacted: MutableList<String>,
    var phonesOldersContacted: MutableList<String>,
    var isChild: Boolean,
    var childPatient: ChildPatient
)
