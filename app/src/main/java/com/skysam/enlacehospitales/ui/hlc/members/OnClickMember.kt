package com.skysam.enlacehospitales.ui.hlc.members

import com.skysam.enlacehospitales.dataClasses.Member

interface OnClickMember {
    fun view(member: Member)
    fun update(member: Member)
    fun delete(member: Member)
    fun changeStatus(member: Member)
}