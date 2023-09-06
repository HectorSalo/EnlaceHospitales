package com.skysam.enlacehospitales.ui.hlc.newHlc

import androidx.recyclerview.widget.DiffUtil
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab

class LabDiffUtil(private val oldList: List<AnalisysLab>, private val newList: List<AnalisysLab>):
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