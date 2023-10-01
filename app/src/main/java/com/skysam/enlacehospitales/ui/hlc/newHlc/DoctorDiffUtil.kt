package com.skysam.enlacehospitales.ui.hlc.newHlc

import androidx.recyclerview.widget.DiffUtil
import com.skysam.enlacehospitales.dataClasses.emergency.Doctor

/**
 * Created by Hector Chirinos on 01/10/2023.
 */

class DoctorDiffUtil(private val oldList: List<Doctor>, private val newList: List<Doctor>):
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