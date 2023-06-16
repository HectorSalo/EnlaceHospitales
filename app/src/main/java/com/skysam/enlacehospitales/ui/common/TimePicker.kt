package com.skysam.enlacehospitales.ui.common

import androidx.fragment.app.FragmentActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.EnlaceHospitales
import java.util.Calendar

class TimePicker(private val fragmentActivity: FragmentActivity,
                 private val onClickDateTime: OnClickDateTime) {
    fun viewTimer() {
        val calendar = Calendar.getInstance()
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(calendar[Calendar.HOUR_OF_DAY])
            .setMinute(Calendar.MINUTE)
            .setTitleText(EnlaceHospitales.EnlaceHospitales.getContext()
                .getString(R.string.title_timepicker_dialog))
            .build()
        picker.addOnPositiveButtonClickListener {
            onClickDateTime.timeSelected(picker.hour, picker.minute)
        }
        picker.show(fragmentActivity.supportFragmentManager, picker.tag)
    }
}