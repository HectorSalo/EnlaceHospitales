package com.skysam.enlacehospitales.ui.common

import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class DatePicker(private val fragmentActivity: FragmentActivity,
                 private val onClickDateTime: OnClickDateTime) {
    fun viewCalendar() {
        val calendar = Calendar.getInstance()
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selection: Long? ->
            calendar.timeInMillis = selection!!
            val timeZone = TimeZone.getDefault()
            val offset = timeZone.getOffset(Date().time) * -1
            calendar.timeInMillis = calendar.timeInMillis + offset
            onClickDateTime.dateSelected(calendar.time)
        }
        picker.show(fragmentActivity.supportFragmentManager, picker.tag)
    }
}