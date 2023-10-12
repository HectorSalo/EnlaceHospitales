package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.TransferPatient
import com.skysam.enlacehospitales.databinding.FragmentTransferBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 03/10/2023.
 */

class TransferDialog: DialogFragment() {
 private var _binding: FragmentTransferBinding? = null
 private val binding get() = _binding!!
 private val viewModel: EmergencysViewModel by activityViewModels()
 private lateinit var emergency: Emergency
 private lateinit var buttonPositive: Button
 private lateinit var buttonNegative: Button

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  _binding = FragmentTransferBinding.inflate(layoutInflater)

  binding.btnNext.visibility = View.GONE
  binding.btnBack.visibility = View.GONE

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
      binding.etDoctor.focusable = View.NOT_FOCUSABLE
      binding.etHospital.focusable = View.NOT_FOCUSABLE
      binding.etInformation.focusable = View.NOT_FOCUSABLE
      binding.etContact.focusable = View.NOT_FOCUSABLE
     } else {
      binding.etDoctor.isEnabled = false
      binding.etHospital.isEnabled = false
      binding.etInformation.isEnabled = false
      binding.etContact.isEnabled = false
     }

     binding.cbContactedSection.isClickable = false
     binding.cbPlans.isClickable = false
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
    binding.etDoctor.setText(it.transferPatient?.nameDoctor)
    binding.etContact.setText(it.transferPatient?.phoneHospital)
    binding.etHospital.setText(it.transferPatient?.nameHospital)
    binding.etInformation.setText(it.transferPatient?.information)
    binding.cbPlans.isChecked = it.transferPatient!!.isPlansConfirmed
    binding.cbContactedSection.isChecked = it.transferPatient!!.isContactedInformationHospitals
   }
  }
 }

 private fun validateData() {
  val hospital = binding.etHospital.text.toString()
  if (hospital.isEmpty()) {
   binding.tfHosptal.error = getString(R.string.error_field_empty)
   binding.etHospital.requestFocus()
   return
  }

  Utils.close(binding.root)
  val transferPatient = TransferPatient(
    binding.cbPlans.isChecked,
    binding.cbContactedSection.isChecked,
    hospital,
    binding.etDoctor.text.toString().ifEmpty { "" },
    binding.etContact.text.toString().ifEmpty { "" },
    binding.etInformation.text.toString().ifEmpty { "" },
   )
  viewModel.updateTransfer(emergency, transferPatient)
  dismiss()
 }

 override fun onDestroyView() {
  super.onDestroyView()
  _binding = null
 }
}