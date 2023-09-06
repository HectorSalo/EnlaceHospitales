package com.skysam.enlacehospitales.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.skysam.enlacehospitales.BuildConfig
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab
import java.text.Collator
import java.text.DateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale

object Utils {
    fun getEnviroment(): String {
        return BuildConfig.BUILD_TYPE
    }

    fun close(view: View) {
        val imn = EnlaceHospitales.EnlaceHospitales.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun convertDoubleToString(value: Double): String {
        return String.format(Locale.GERMANY, "%,.2f", value)
    }

    fun convertStringToDouble(value: String): Double {
        var number = 0.0
        var str = ""
        if (value.isNotEmpty()) {
            str = value.replace(".","").replace(",", ".")
            number = str.toDouble()
        }
        return number
    }

    fun convertDateToString(value: Date): String {
        return DateFormat.getDateInstance().format(value)
    }
    fun convertDateTimeToString(value: Date): String {
        return DateFormat.getDateTimeInstance().format(value)
    }

    fun organizedAlphabeticListMembers(list: MutableList<Member>): List<Member> {
        Collections.sort(list, object : Comparator<Member> {
            var collator = Collator.getInstance()
            override fun compare(p0: Member?, p1: Member?): Int {
                return collator.compare(p0?.name, p1?.name)
            }

        })
        return list
    }

    fun organizedDatesListLabs(list: MutableList<AnalisysLab>): List<AnalisysLab> {
        Collections.sort(list, object : Comparator<AnalisysLab> {
            var collator = Collator.getInstance()
            override fun compare(p0: AnalisysLab?, p1: AnalisysLab?): Int {
                return collator.compare(p0?.date, p1?.date)
            }

        })
        return list
    }
}