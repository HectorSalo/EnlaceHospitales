package com.skysam.enlacehospitales.ui.hlc.members

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
import com.skysam.enlacehospitales.dataClasses.Member

class MemberAdapter(private val onClickMember: OnClickMember): RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    lateinit var context: Context
    private var members = listOf<Member>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_member, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberAdapter.ViewHolder, position: Int) {
        val item = members[position]
        holder.member.text = item.name
        holder.phone.text = item.phone
        holder.speciality.text = item.speciality

        holder.card.setOnClickListener { onClickMember.view(item) }
        if (EnlaceHospitales.EnlaceHospitales.getCurrentUser().role == Constants.ROLE_ADMIN) {
            holder.card.setOnLongClickListener {
                val popMenu = PopupMenu(context, holder.card)
                popMenu.inflate(R.menu.menu_members_item)
                popMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_edit -> onClickMember.update(item)
                        R.id.menu_delete -> onClickMember.delete(item)
                    }
                    false
                }
                popMenu.show()
                true
            }
        }
    }

    override fun getItemCount(): Int = members.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val member: TextView = view.findViewById(R.id.tv_member)
        val phone: TextView = view.findViewById(R.id.tv_phone)
        val speciality: TextView = view.findViewById(R.id.tv_speciality)
        val card: MaterialCardView = view.findViewById(R.id.card)
    }

    fun updateList(newList: List<Member>) {
        val diffUtil = MemberDiffUtil(members, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        members = newList
        result.dispatchUpdatesTo(this)
    }
}