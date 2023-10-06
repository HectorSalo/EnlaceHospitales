package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.databinding.DialogHospitalDetailsBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 29/08/2023.
 */

class HospitalDialog: DialogFragment() {
 private var _binding: DialogHospitalDetailsBinding? = null
 private val binding get() = _binding!!
 private val viewModel: EmergencysViewModel by activityViewModels()
 private lateinit var emergency: Emergency
 private lateinit var buttonPositive: Button
 private lateinit var buttonNegative: Button

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  _binding = DialogHospitalDetailsBinding.inflate(layoutInflater)

  binding.etName.doAfterTextChanged { binding.tfName.error = null }

  val builder = AlertDialog.Builder(requireActivity())
  builder.setTitle(getString(R.string.text_details))
   .setView(binding.root)
   .setPositiveButton(R.string.text_accept, null)
   .setNegativeButton(R.string.text_cancel, null)

  val dialog = builder.create()
  dialog.show()

  buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
  buttonNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)

  subscribeViewModel()

  return dialog
 }

 private fun subscribeViewModel() {
  viewModel.isView.observe(this.requireActivity()) {
   if (_binding != null) {
    if (it) {
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      binding.etName.focusable = View.NOT_FOCUSABLE
      binding.etRoom.focusable = View.NOT_FOCUSABLE
      binding.etNameOlders.focusable = View.NOT_FOCUSABLE
      binding.etPhoneOlders.focusable = View.NOT_FOCUSABLE
     } else {
      binding.etName.isEnabled = false
      binding.etRoom.isEnabled = false
      binding.etNameOlders.isEnabled = false
      binding.etPhoneOlders.isEnabled = false
     }
     buttonNegative.visibility = View.GONE
    } else {
     buttonPositive.setText(R.string.text_update)
     buttonPositive.setOnClickListener { validateData() }
    }
   }
  }
  viewModel.emergencyToView.observe(this.requireActivity()) {
   if (_binding != null) {
    emergency = it
    binding.etName.setText(it.hospital?.nameHospital)
    binding.etRoom.setText(it.hospital?.room)
    binding.etNameOlders.setText(it.hospital?.namesOldersContacted)
    binding.etPhoneOlders.setText(it.hospital?.phonesOldersContacted)
   }
  }
 }

 override fun onDestroyView() {
  super.onDestroyView()
  _binding = null
 }

 private fun validateData() {
  val name = binding.etName.text.toString()
  if (name.isEmpty()) {
   binding.tfName.error = getString(R.string.error_field_empty)
   binding.etName.requestFocus()
   return
  }

  val hospital = Hospital(
   name,
   binding.etRoom.text.toString(),
   binding.etNameOlders.text.toString(),
   binding.etPhoneOlders.text.toString()
  )

  viewModel.updateHospital(emergency, hospital)
  dismiss()
 }
}