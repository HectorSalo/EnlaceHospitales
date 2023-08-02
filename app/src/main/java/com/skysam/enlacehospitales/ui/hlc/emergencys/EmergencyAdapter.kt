package com.skysam.enlacehospitales.ui.hlc.emergencys

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency

class EmergencyAdapter: RecyclerView.Adapter<EmergencyAdapter.ViewHolder>() {
    lateinit var context: Context
    private var emergencys = listOf<Emergency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_emergency, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmergencyAdapter.ViewHolder, position: Int) {
        val item = emergencys[position]
        holder.patient.text = item.patient.name
        holder.dateCreated.text = Utils.convertDateTimeToString(item.notification.dateCall)
        holder.dateUpdated.text = Utils.convertDateToString(item.dateUdpdated)
        holder.hospital.text = item.patient.nameHospital
        holder.status.text = item.status
    }

    override fun getItemCount(): Int = emergencys.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val patient: TextView = view.findViewById(R.id.tv_patient)
        val dateCreated: TextView = view.findViewById(R.id.tv_date_created)
        val dateUpdated: TextView = view.findViewById(R.id.tv_date_updated)
        val hospital: TextView = view.findViewById(R.id.tv_hospital)
        val status: TextView = view.findViewById(R.id.tv_status)
        val card: MaterialCardView = view.findViewById(R.id.card)
    }

    fun updateList(newList: List<Emergency>) {
        val diffUtil = EmergencyDiffUtil(emergencys, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        emergencys = newList
        result.dispatchUpdatesTo(this)
    }
}