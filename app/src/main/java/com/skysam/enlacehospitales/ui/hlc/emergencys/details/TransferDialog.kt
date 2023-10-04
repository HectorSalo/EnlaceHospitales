package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentTransferBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 03/10/2023.
 */

class TransferDialog: DialogFragment() {
 private var _binding: FragmentTransferBinding? = null
 private val binding get() = _binding!!
 private val viewModel: EmergencysViewModel by activityViewModels()

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  _binding = FragmentTransferBinding.inflate(layoutInflater)

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
  binding.btnNext.visibility = View.GONE
  binding.btnBack.visibility = View.GONE
  binding.cbContactedSection.isClickable = false
  binding.cbPlans.isClickable = false

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
  viewModel.emergencyToView.observe(this.requireActivity()) {
   if (_binding != null) {
    binding.etDoctor.setText(it.transferPatient?.nameDoctor)
    binding.etContact.setText(it.transferPatient?.phoneHospital)
    binding.etHospital.setText(it.transferPatient?.nameHospital)
    binding.etInformation.setText(it.transferPatient?.information)
    binding.cbPlans.isChecked = it.transferPatient!!.isPlansConfirmed
    binding.cbContactedSection.isChecked = it.transferPatient!!.isContactedInformationHospitals
   }
  }
 }

 override fun onDestroyView() {
  super.onDestroyView()
  _binding = null
 }
}