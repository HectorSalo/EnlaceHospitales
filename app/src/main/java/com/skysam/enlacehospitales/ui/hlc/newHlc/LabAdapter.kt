package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab

class LabAdapter: RecyclerView.Adapter<LabAdapter.ViewHolder>() {
    lateinit var context: Context
    private var labs = listOf<AnalisysLab>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_lab, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabAdapter.ViewHolder, position: Int) {
        val item = labs[position]
        holder.date.setText(Utils.convertDateTimeToString(item.date))
        holder.hemoglobina.setText(Utils.convertDoubleToString(item.hemoglobina))
        holder.hematocrito.setText(Utils.convertDoubleToString(item.hematocrito))
        holder.plaquetas.setText(Utils.convertDoubleToString(item.plaquetas))
        holder.others.setText(item.others)
    }

    override fun getItemCount(): Int = labs.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextInputEditText = view.findViewById(R.id.et_date)
        val hemoglobina: TextInputEditText = view.findViewById(R.id.et_hemoglobina)
        val hematocrito: TextInputEditText = view.findViewById(R.id.et_hematocrito)
        val plaquetas: TextInputEditText = view.findViewById(R.id.et_plaquetas)
        val others: TextInputEditText = view.findViewById(R.id.et_others)
    }

    fun updateList(newList: List<AnalisysLab>) {
        val diffUtil = LabDiffUtil(labs, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        labs = newList
        result.dispatchUpdatesTo(this)
    }
}