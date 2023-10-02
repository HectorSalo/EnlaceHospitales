package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.databinding.DialogHospitalDetailsBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 29/08/2023.
 */

class HospitalDialog: DialogFragment() {
 private var _binding: DialogHospitalDetailsBinding? = null
 private val binding get() = _binding!!
 private val viewModel: EmergencysViewModel by activityViewModels()

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  _binding = DialogHospitalDetailsBinding.inflate(layoutInflater)

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
    binding.etName.setText(it.hospital?.nameHospital)
    if (it.hospital?.room!!.isEmpty()) {
     binding.tfRoom.visibility = View.GONE
    }
    binding.etRoom.setText(EnlaceHospitales.EnlaceHospitales.getContext()
     .getString(R.string.text_room_details, it.hospital.room))
    binding.etNameOlders.setText(it.hospital.namesOldersContacted)
    binding.etPhoneOlders.setText(it.hospital.phonesOldersContacted)
   }
  }
 }

 override fun onDestroyView() {
  super.onDestroyView()
  _binding = null
 }
}