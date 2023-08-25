package com.skysam.enlacehospitales.ui.hlc.members

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.repositories.MembersHlc

class MembersViewModel : ViewModel() {
    val members: LiveData<List<Member>> = MembersHlc.getMembers().asLiveData()

    private val _memberToView = MutableLiveData<Member>()
    val memberToView: LiveData<Member> get() = _memberToView

    private val _memberToUpdate = MutableLiveData<Member>()
    val memberToUpdate: LiveData<Member> get() = _memberToUpdate
    fun saveMember(member: Member) {
        MembersHlc.saveMember(member)
    }

    fun viewMember(member: Member) {
        _memberToView.value = member
    }

    fun memberToDialogUpdate(member: Member) {
        _memberToUpdate.value = member
    }

    fun updateMember(member: Member) {
        MembersHlc.updateMember(member)
    }

    fun deleteMember(member: Member) {
        MembersHlc.deleteMember(member.id)
    }
}