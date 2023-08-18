package com.skysam.enlacehospitales.ui.hlc.members

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.databinding.DialogViewMemberDetailsBinding

class ViewDetailsDialog: DialogFragment() {
    private var _binding: DialogViewMemberDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MembersViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogViewMemberDetailsBinding.inflate(layoutInflater)

        subscribeViewModel()

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.text_details))
            .setView(binding.root)
            .setPositiveButton(R.string.text_accept, null)

        val dialog = builder.create()
        dialog.show()

        return dialog
    }



    private fun subscribeViewModel() {
        viewModel.memberToView.observe(this.requireActivity()) {
            if (_binding != null) {
                binding.tvName.text = it.name
                binding.tvEmail.text = it.email
                binding.tvPassword.text = EnlaceHospitales.EnlaceHospitales.getContext().getString(R.string.text_password_from,
                    it.password)
                binding.tvPassword.text = EnlaceHospitales.EnlaceHospitales.getContext().getString(R.string.text_password_from,
                    it.password)
                binding.tvCongregation.text = EnlaceHospitales.EnlaceHospitales.getContext()
                    .getString(R.string.text_congregation_from, it.congregation)
                binding.tvPhone.text = it.phone
                binding.tvStatus.text = if (it.isActive) EnlaceHospitales.EnlaceHospitales.getContext()
                    .getString(R.string.text_active) else EnlaceHospitales.EnlaceHospitales.getContext()
                        .getString(R.string.text_inactive)
                binding.tvDateCreated.text = EnlaceHospitales.EnlaceHospitales.getContext()
                    .getString(R.string.text_date_created_account_from,
                    Utils.convertDateToString(it.dateCreated))
                binding.tvRole.text = when(it.role) {
                    Constants.ROLE_ADMIN -> EnlaceHospitales.EnlaceHospitales.getContext().getString(R.string.text_role_from,
                        EnlaceHospitales.EnlaceHospitales.getContext().getString(R.string.role_admin))
                    Constants.ROLE_HLC -> EnlaceHospitales.EnlaceHospitales.getContext().getString(R.string.text_role_from,
                        EnlaceHospitales.EnlaceHospitales.getContext().getString(R.string.role_hlc))
                    Constants.ROLE_GVP -> EnlaceHospitales.EnlaceHospitales.getContext().getString(R.string.text_role_from,
                        EnlaceHospitales.EnlaceHospitales.getContext().getString(R.string.role_gvp))
                    else -> ""
                }

                if (EnlaceHospitales.EnlaceHospitales.getCurrentUser().role != Constants.ROLE_ADMIN) {
                    binding.tvRole.visibility = View.GONE
                    binding.tvPassword.visibility = View.GONE
                }
            }
        }
    }
}