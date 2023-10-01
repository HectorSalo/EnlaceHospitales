package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.dataClasses.emergency.Doctor

/**
 * Created by Hector Chirinos on 01/10/2023.
 */

class DoctorAdapter: RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {
 lateinit var context: Context
 private var doctors = listOf<Doctor>()

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAdapter.ViewHolder {
  val view = LayoutInflater.from(parent.context)
   .inflate(R.layout.layout_item_member, parent, false)
  context = parent.context
  return ViewHolder(view)
 }

 override fun onBindViewHolder(holder: DoctorAdapter.ViewHolder, position: Int) {
  val item = doctors[position]
  holder.member.text = item.name
  holder.speciality.text = item.speciality

  holder.phone.visibility = View.GONE
 }

 override fun getItemCount(): Int = doctors.size

 inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val member: TextView = view.findViewById(R.id.tv_member)
  val phone: TextView = view.findViewById(R.id.tv_phone)
  val speciality: TextView = view.findViewById(R.id.tv_speciality)
  val card: MaterialCardView = view.findViewById(R.id.card)
 }

 fun updateList(newList: List<Doctor>) {
  val diffUtil = DoctorDiffUtil(doctors, newList)
  val result = DiffUtil.calculateDiff(diffUtil)
  doctors = newList
  result.dispatchUpdatesTo(this)
 }
}