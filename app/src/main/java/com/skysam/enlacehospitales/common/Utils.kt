package com.skysam.enlacehospitales.common

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.BuildCompat
import com.skysam.enlacehospitales.BuildConfig
import java.text.Collator
import java.text.DateFormat
import java.util.Collections
import java.util.Comparator
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

    fun convertNumberTwoDecimals(value: String): String {
        val valueR = value.replace(",", ""). replace(".", "")
        val valueD: Double = valueR.toDouble() / 100
        return String.format(Locale.GERMANY, "%,.2f", valueD)
    }

    fun convertDateTimeToString(value: Date): String {
        return DateFormat.getDateInstance().format(value)
    }

    /*fun organizedAlphabeticList(list: MutableList<Booking>): MutableList<Booking> {
        Collections.sort(list, object : Comparator<Booking> {
            var collator = Collator.getInstance()
            override fun compare(p0: Booking?, p1: Booking?): Int {
                return collator.compare(p0?.name, p1?.name)
            }

        })
        return list
    }*/
}