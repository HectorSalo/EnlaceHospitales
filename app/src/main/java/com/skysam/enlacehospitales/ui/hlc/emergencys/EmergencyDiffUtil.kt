package com.skysam.enlacehospitales.ui.hlc.emergencys

import androidx.recyclerview.widget.DiffUtil
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency

class EmergencyDiffUtil(private val oldList: List<Emergency>, private val newList: List<Emergency>):
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