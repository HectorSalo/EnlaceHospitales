package com.skysam.enlacehospitales.common

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity


object BiometricAvailable {
    private val biometricManager = BiometricManager.from(EnlaceHospitales.EnlaceHospitales.getContext())

    fun biometricIsAvailable(): Boolean {
        return when (biometricManager.canAuthenticate(BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> false
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> false
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> false
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> false
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> false
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    fun checkBiometric(activity: FragmentActivity): String {
        var result = ""
        val biometricPrompt: BiometricPrompt
        val executor = ContextCompat.getMainExecutor(EnlaceHospitales.EnlaceHospitales.getContext())
        biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                result = errString.toString()
            }

            override fun onAuthenticationSucceeded(resultBio: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(resultBio)
                result = Constants.RESULT_OK
            }
        })
        return result
    }
}