package com.skysam.enlacehospitales.common

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.ui.login.BiometricChecked


class Biometric(private val biometricChecked: BiometricChecked) {
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

    fun checkBiometric(activity: FragmentActivity) {
        val biometricPrompt: BiometricPrompt
        val executor = ContextCompat.getMainExecutor(EnlaceHospitales.EnlaceHospitales.getContext())
        biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                biometricChecked.check(errString.toString())
            }

            override fun onAuthenticationSucceeded(resultBio: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(resultBio)
                biometricChecked.check(Constants.RESULT_OK)
            }
        })

        val promptInfo = PromptInfo.Builder()
            .setTitle(EnlaceHospitales.EnlaceHospitales.getContext()
                .getString(com.firebase.ui.auth.R.string.fui_welcome_back_email_header))
            .setSubtitle(EnlaceHospitales.EnlaceHospitales.getContext().getString(R.string.text_put_fingerprint))
            .setNegativeButtonText(EnlaceHospitales.EnlaceHospitales.getContext().getString(R.string.text_cancel))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}