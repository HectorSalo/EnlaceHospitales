package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentDoctorBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel
import com.skysam.enlacehospitales.ui.hlc.newHlc.DoctorAdapter

/**
 * Created by Hector Chirinos on 03/10/2023.
 */

class DoctorDialog: DialogFragment() {
 private var _binding: FragmentDoctorBinding? = null
 private val binding get() = _binding!!
 private val viewModel: EmergencysViewModel by activityViewModels()
 private lateinit var doctorAdapter: DoctorAdapter

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  _binding = FragmentDoctorBinding.inflate(layoutInflater)

  doctorAdapter = DoctorAdapter()
  binding.rvDoctor.apply {
   setHasFixedSize(true)
   adapter = doctorAdapter
  }
  binding.fab.hide()
  binding.btnBack.visibility = View.GONE
  binding.btnNext.visibility = View.GONE

  subscribeViewModel()

  val builder = AlertDialog.Builder(requireActivity())
  builder.setTitle(getString(R.string.text_details))
   .setView(binding.root)
   .setPositiveButton(R.string.text_accept, null)
   .setNegativeButton(R.string.text_new_doctor) { _, _ ->
    viewModel.newDoctor(true)
    dismiss()
   }

  val dialog = builder.create()
  dialog.show()

  return dialog
 }

 private fun subscribeViewModel() {
  viewModel.emergencyToView.observe(this.requireActivity()) {
   if (_binding != null) {
    if (it.doctors.isNotEmpty()) {
     doctorAdapter.updateList(it.doctors)
     binding.rvDoctor.visibility = View.VISIBLE
     binding.tvListEmpty.visibility = View.GONE
    } else binding.tvListEmpty.text = getString(R.string.text_list_empty)
   }
  }
 }

 override fun onDestroyView() {
  super.onDestroyView()
  _binding = null
 }
}