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
import com.skysam.enlacehospitales.common.Utils
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
        holder.dateCreated.text = Utils.convertDateToString(item.dateCreated)
        holder.phone.text = item.phone
        holder.status.text = if (item.isActive) context.getString(R.string.text_active)
        else context.getString(R.string.text_inactive)

        holder.card.setOnClickListener { onClickMember.view(item) }
        if (EnlaceHospitales.EnlaceHospitales.getCurrentUser().role == Constants.ROLE_ADMIN) {
            holder.card.setOnLongClickListener {
                val popMenu = PopupMenu(context, holder.card)
                popMenu.inflate(R.menu.menu_members_item)
                popMenu.menu.getItem(1).title = if (item.isActive) context.getString(R.string.text_inactive_menu)
                else context.getString(R.string.text_active_menu)
                popMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_edit -> onClickMember.update(item)
                        R.id.menu_status -> onClickMember.changeStatus(item)
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
        val dateCreated: TextView = view.findViewById(R.id.tv_date_created)
        val phone: TextView = view.findViewById(R.id.tv_phone)
        val status: TextView = view.findViewById(R.id.tv_status)
        val card: MaterialCardView = view.findViewById(R.id.card)
    }

    fun updateList(newList: List<Member>) {
        val diffUtil = MemberDiffUtil(members, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        members = newList
        result.dispatchUpdatesTo(this)
    }
}