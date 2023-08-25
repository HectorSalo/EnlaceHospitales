package com.skysam.enlacehospitales.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.ui.hlc.members.MemberDiffUtil

/**
 * Created by Hector Chirinos on 13/08/2023.
 */

class GuardAdapter(private val onClick: OnClick):
 RecyclerView.Adapter<GuardAdapter.ViewHolder>() {
 private lateinit var context: Context
 private var members = listOf<Member>()

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardAdapter.ViewHolder {
  val view = LayoutInflater.from(parent.context)
   .inflate(R.layout.layout_guard_item, parent, false)
  context = parent.context
  return ViewHolder(view)
 }

 override fun onBindViewHolder(holder: GuardAdapter.ViewHolder, position: Int) {
  val item = members[position]
  holder.name.text = item.name
  holder.congregation.text = item.congregation
  if (item.congregation.isEmpty()) {
   holder.congregation.visibility = View.GONE
  } else {
   holder.congregation.visibility = View.VISIBLE
  }
  holder.phone.text = item.phone
  if (item.phone.isEmpty()) {
   holder.phone.visibility = View.GONE
  } else {
   holder.phone.visibility = View.VISIBLE
  }

  holder.share.setOnClickListener { onClick.share(item) }
  holder.call.setOnClickListener { onClick.call(item) }
 }

 override fun getItemCount(): Int = members.size

 inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val title: TextView = view.findViewById(R.id.tv_title)
  val name: TextView = view.findViewById(R.id.tv_name)
  val congregation: TextView = view.findViewById(R.id.tv_congregation)
  val phone: TextView = view.findViewById(R.id.tv_phone)
  val share: MaterialButton = view.findViewById(R.id.btn_share)
  val call: MaterialButton = view.findViewById(R.id.btn_call)
  val card: MaterialCardView = view.findViewById(R.id.card)
 }

 fun updateList(newList: List<Member>) {
  val diffUtil = MemberDiffUtil(members, newList)
  val result = DiffUtil.calculateDiff(diffUtil)
  members = newList
  result.dispatchUpdatesTo(this)
 }
}