package com.skysam.enlacehospitales.dataClasses.emergency

data class Hospital(
    var nameHospital: String,
    var room: String,
    var phone: String,
    var namesOldersContacted: List<String>,
    var phonesOldersContacted: List<String>,
)
