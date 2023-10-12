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
import com.skysam.enlacehospitales.dataClasses.emergency.Tracing
import com.skysam.enlacehospitales.databinding.FragmentResultsBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 03/10/2023.
 */

class ResultsDialog: DialogFragment() {
 private var _binding: FragmentResultsBinding? = null
 private val binding get() = _binding!!
 private val viewModel: EmergencysViewModel by activityViewModels()
 private lateinit var emergency: Emergency
 private lateinit var buttonPositive: Button
 private lateinit var buttonNegative: Button

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  _binding = FragmentResultsBinding.inflate(layoutInflater)

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
      binding.etResults.focusable = View.NOT_FOCUSABLE
     } else {
      binding.etResults.isEnabled = false
     }
     binding.cbOldersContacted.isClickable = false
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
    binding.etResults.setText(it.tracing?.results)
    binding.cbOldersContacted.isChecked = it.tracing!!.isContactedOldersLocals
   }
  }
 }

 private fun validateData() {
  val results = binding.etResults.text.toString()
  if (results.isEmpty()) {
   binding.tfResults.error = getString(R.string.error_field_empty)
   binding.etResults.requestFocus()
   return
  }

  Utils.close(binding.root)
  val tracing = Tracing(
    binding.cbOldersContacted.isChecked,
    results
   )
  viewModel.updateResults(emergency, tracing)
 }
 override fun onDestroyView() {
  super.onDestroyView()
  _binding = null
 }
}