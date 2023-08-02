package com.skysam.enlacehospitales.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.repositories.Members

class MainViewModel: ViewModel() {
    val members: LiveData<List<Member>> = Members.getMembers().asLiveData()
}