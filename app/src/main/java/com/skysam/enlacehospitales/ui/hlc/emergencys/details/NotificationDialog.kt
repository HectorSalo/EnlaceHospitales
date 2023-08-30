package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.databinding.DialogNotificationDetailsBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 29/08/2023.
 */

class NotificationDialog: DialogFragment() {
 private var _binding: DialogNotificationDetailsBinding? = null
 private val binding get() = _binding!!
 private val viewModel: EmergencysViewModel by activityViewModels()

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  _binding = DialogNotificationDetailsBinding.inflate(layoutInflater)

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
   binding.etName.focusable = View.NOT_FOCUSABLE
   binding.etRelationship.focusable = View.NOT_FOCUSABLE
   binding.etDate.focusable = View.NOT_FOCUSABLE
   binding.etInformation.focusable = View.NOT_FOCUSABLE
  } else {
   binding.etName.isEnabled = false
   binding.etRelationship.isEnabled = false
   binding.etDate.isEnabled = false
   binding.etInformation.isEnabled = false
  }

  binding.etDate.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
  binding.checkBox.isClickable = false

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
  viewModel.notificationToView.observe(this.requireActivity()) {
   if (_binding != null) {
    binding.etName.setText(it.personCall)
    binding.etRelationship.setText(it.relationshipPatient)
    binding.etDate.setText(Utils.convertDateTimeToString(it.dateCall))
    binding.etInformation.setText(it.infoPersonCall)
    binding.checkBox.isChecked = it.isNeedHelp
   }
  }
 }

 override fun onDestroyView() {
  super.onDestroyView()
  _binding = null
 }
}