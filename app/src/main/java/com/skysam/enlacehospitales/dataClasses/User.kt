package com.skysam.enlacehospitales.dataClasses

data class User(
    val id: String,
    var name: String,
    val email: String,
    var phone: String,
    var role: String
)
