package com.skysam.enlacehospitales.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.common.Preferences
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.repositories.MembersHlc

class LoginViewModel: ViewModel() {
    val members: LiveData<List<Member>> = MembersHlc.getMembers().asLiveData()
    val emailLast: LiveData<String> = Preferences.getEmailSaved().asLiveData()
    val passwordLast: LiveData<String> = Preferences.getPasswordSaved().asLiveData()
    val biometricEnable: LiveData<Boolean> = Preferences.isBiometricEnable().asLiveData()

    suspend fun saveLastSession(email: String, password: String, isEnable: Boolean) {
        Preferences.setEmailSaved(email)
        Preferences.setPasswordSaved(password)
        Preferences.setBiometric(isEnable)
    }
}