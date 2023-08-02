package com.skysam.enlacehospitales.ui.hlc.members

import androidx.recyclerview.widget.DiffUtil
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency

class MemberDiffUtil (private val oldList: List<Member>, private val newList: List<Member>):
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList.contains(oldList[oldItemPosition])
    }
}