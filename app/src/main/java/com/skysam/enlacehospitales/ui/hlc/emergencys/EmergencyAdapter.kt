package com.skysam.enlacehospitales.ui.hlc.emergencys

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency

class EmergencyAdapter(private val onClick: OnClick): RecyclerView.Adapter<EmergencyAdapter.ViewHolder>() {
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
        holder.patient.text = item.patient?.name
        holder.dateCall.text = context.getString(R.string.text_date_call_item,
            Utils.convertDateTimeToString(item.notification.dateCall))
        holder.dateUpdated.text = context.getString(R.string.text_date_updated_item,
            Utils.convertDateToString(item.dateUdpdated))
        holder.hospital.text = item.hospital.nameHospital
        holder.phone.text = item.patient?.phone
        if (item.speciality.isNotEmpty()) {
            holder.speciality.text = item.speciality
            holder.speciality.visibility = View.VISIBLE
        } else holder.speciality.visibility = View.GONE


        if (EnlaceHospitales.EnlaceHospitales.getCurrentUser().role == Constants.ROLE_ADMIN) {
            holder.card.setOnLongClickListener {
                val popMenu = PopupMenu(context, holder.card)
                popMenu.inflate(R.menu.menu_emergency_item)
                popMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_finish -> onClick.finish(item)
                    }
                    false
                }
                popMenu.show()
                true
            }
        }

        holder.card.setOnClickListener { onClick.view(item) }
    }

    override fun getItemCount(): Int = emergencys.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val patient: TextView = view.findViewById(R.id.tv_patient)
        val dateCall: TextView = view.findViewById(R.id.tv_date_call)
        val dateUpdated: TextView = view.findViewById(R.id.tv_date_updated)
        val hospital: TextView = view.findViewById(R.id.tv_hospital)
        val phone: TextView = view.findViewById(R.id.tv_phone)
        val speciality: TextView = view.findViewById(R.id.tv_speciality)
        val card: MaterialCardView = view.findViewById(R.id.card)
    }

    fun updateList(newList: List<Emergency>) {
        val diffUtil = EmergencyDiffUtil(emergencys, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        emergencys = newList
        result.dispatchUpdatesTo(this)
    }
}