package com.skysam.enlacehospitales.ui.common

import java.util.Date

interface OnClickDateTime {
    fun dateSelected(date: Date)
    fun timeSelected(hour: Int, minute: Int)
}