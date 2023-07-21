package com.skysam.enlacehospitales.dataClasses.emergency

import java.util.Date

data class Notification(
    var dateCall: Date,
    var personCall: String,
    var relationshipPatient: String,
    var infoPersonCall: String,
    var isNeedHelp: Boolean
)
