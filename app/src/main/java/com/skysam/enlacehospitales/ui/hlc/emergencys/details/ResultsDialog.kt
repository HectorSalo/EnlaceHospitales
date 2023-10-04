package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentArticlesBinding
import com.skysam.enlacehospitales.databinding.FragmentResultsBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 03/10/2023.
 */

class ResultsDialog: DialogFragment() {
 private var _binding: FragmentResultsBinding? = null
 private val binding get() = _binding!!
 private val viewModel: EmergencysViewModel by activityViewModels()

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  _binding = FragmentResultsBinding.inflate(layoutInflater)

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
   binding.etResults.focusable = View.NOT_FOCUSABLE
  } else {
   binding.etResults.isEnabled = false
  }
  binding.btnNext.visibility = View.GONE
  binding.btnBack.visibility = View.GONE
  binding.cbOldersContacted.isClickable = false

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
    binding.etResults.setText(it.tracing?.results)
    binding.cbOldersContacted.isChecked = it.tracing!!.isContactedOldersLocals
   }
  }
 }

 override fun onDestroyView() {
  super.onDestroyView()
  _binding = null
 }
}