package com.skysam.enlacehospitales.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.common.Preferences
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.repositories.MembersHlc

class MainViewModel: ViewModel() {
    val members: LiveData<List<Member>> = MembersHlc.getMembers().asLiveData()
    val fingerprintActive: LiveData<Boolean> = Preferences.isBiometricEnable().asLiveData()

    suspend fun changeBiometric(status: Boolean) {
        Preferences.setBiometric(status)
    }

    suspend fun signOut() {
        Preferences.setEmailSaved("")
        Preferences.setPasswordSaved("")
        Preferences.setBiometric(false)
    }
}