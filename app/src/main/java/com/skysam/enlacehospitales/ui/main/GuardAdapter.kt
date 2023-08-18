package com.skysam.enlacehospitales.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.ui.hlc.members.MemberDiffUtil

/**
 * Created by Hector Chirinos on 13/08/2023.
 */

class GuardAdapter(private val onClickGuard: OnClickGuard):
 RecyclerView.Adapter<GuardAdapter.ViewHolder>() {
 private lateinit var context: Context
 private var members = listOf<Member>()

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardAdapter.ViewHolder {
  val view = LayoutInflater.from(parent.context)
   .inflate(R.layout.layout_carousel_item, parent, false)
  context = parent.context
  return ViewHolder(view)
 }

 override fun onBindViewHolder(holder: GuardAdapter.ViewHolder, position: Int) {
  val item = members[position]
  holder.title.text  = if (item.role != Constants.ROLE_ADMIN) context.getString(R.string.text_guard) + " " +
          context.getString(R.string.title_gvp) else context.getString(R.string.text_guard) + " " +
          context.getString(R.string.title_hlc)
  holder.name.text = item.name
  holder.card.setOnClickListener { onClickGuard.view(item) }
 }

 override fun getItemCount(): Int = members.size

 inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val title: TextView = view.findViewById(R.id.tv_title)
  val name: TextView = view.findViewById(R.id.tv_name)
  val card: MaterialCardView = view.findViewById(R.id.card)
 }

 fun updateList(newList: List<Member>) {
  val diffUtil = MemberDiffUtil(members, newList)
  val result = DiffUtil.calculateDiff(diffUtil)
  members = newList
  result.dispatchUpdatesTo(this)
 }
}