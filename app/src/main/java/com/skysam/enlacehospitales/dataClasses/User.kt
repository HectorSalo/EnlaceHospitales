package com.skysam.enlacehospitales.dataClasses

data class User(
    val id: String,
    var name: String,
    var email: String,
    var congregation: String,
    var phone: String,
    var role: String
)
