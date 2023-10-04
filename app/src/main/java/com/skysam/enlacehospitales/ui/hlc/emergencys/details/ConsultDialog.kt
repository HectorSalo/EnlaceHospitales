package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentConsultBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 03/10/2023.
 */

class ConsultDialog: DialogFragment() {
    private var _binding: FragmentConsultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmergencysViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentConsultBinding.inflate(layoutInflater)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.etInformation.focusable = View.NOT_FOCUSABLE
            binding.etName.focusable = View.NOT_FOCUSABLE
            binding.etContact.focusable = View.NOT_FOCUSABLE
            binding.etSpecality.focusable = View.NOT_FOCUSABLE
        } else {
            binding.etInformation.isEnabled = false
            binding.etName.isEnabled = false
            binding.etContact.isEnabled = false
            binding.etSpecality.isEnabled = false
        }
        binding.btnNext.visibility = View.GONE
        binding.btnBack.visibility = View.GONE

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
                binding.etInformation.setText(it.secondDoctor?.information)
                binding.etSpecality.setText(it.secondDoctor?.speciality)
                binding.etName.setText(it.secondDoctor?.name)
                binding.etContact.setText(it.secondDoctor?.methodContact)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}