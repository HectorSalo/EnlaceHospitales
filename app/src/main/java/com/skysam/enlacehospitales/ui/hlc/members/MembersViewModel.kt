package com.skysam.enlacehospitales.ui.hlc.members

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.repositories.Members

class MembersViewModel : ViewModel() {
    val members: LiveData<List<Member>> = Members.getMembers().asLiveData()
}