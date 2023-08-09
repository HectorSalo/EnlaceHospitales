package com.skysam.enlacehospitales.common

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Created by Hector Chirinos on 03/08/2023.
 */

object Permission {
    fun checkPermissionCall(): Boolean {
        val result = ContextCompat.checkSelfPermission(EnlaceHospitales.EnlaceHospitales.getContext(), Manifest.permission.CALL_PHONE)
        return result == PackageManager.PERMISSION_GRANTED
    }
}