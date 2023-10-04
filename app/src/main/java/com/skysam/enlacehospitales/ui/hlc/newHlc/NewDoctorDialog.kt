package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.Doctor
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.databinding.DialogNewDoctorBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 01/10/2023.
 */

class NewDoctorDialog(private val fromEmergency: Boolean): DialogFragment() {
 private var _binding: DialogNewDoctorBinding? = null
 private val binding get() = _binding!!
 private val viewModelHcl: NewHclViewModel by activityViewModels()
 private val viewModelEmergency: EmergencysViewModel by activityViewModels()
 private lateinit var emergency: Emergency

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  _binding = DialogNewDoctorBinding.inflate(layoutInflater)

  subscribeViewModel()

  val builder = AlertDialog.Builder(requireActivity())
  builder.setTitle(getString(R.string.text_new_doctor))
   .setView(binding.root)
   .setPositiveButton(R.string.text_save) { _, _ ->
    sendData()
   }
   .setNegativeButton(R.string.text_cancel, null)

  val dialog = builder.create()
  dialog.show()

  return dialog
 }



 private fun subscribeViewModel() {
  viewModelEmergency.emergencyToView.observe(this.requireActivity()) {
   if (_binding != null) {
    emergency = it
   }
  }
 }

 private fun sendData() {
  Utils.close(binding.root)
  val name = binding.etName.text.toString()
  if (name.isEmpty()) {
   binding.tfName.error = getString(R.string.error_field_empty)
   binding.etName.requestFocus()
   return
  }
  val speciality = binding.etSpeciality.text.toString()
  if (speciality.isEmpty()) {
   binding.tfSpeciality.error = getString(R.string.error_field_empty)
   binding.etSpeciality.requestFocus()
   return
  }

  val doctor = Doctor(
   name,
   speciality,
   "",
   ""
  )

  if (!fromEmergency) viewModelHcl.setDoctors(doctor) else viewModelEmergency.saveNewDoctor(emergency, doctor)
  dismiss()
 }
}