package com.skysam.enlacehospitales.dataClasses

import java.util.Date

data class Member(
    val id: String,
    var name: String,
    var email: String,
    var password: String,
    var congregation: String,
    var phone: String,
    var role: String,
    val dateCreated: Date,
    var speciality: String,
    val guard: List<Int>
)
