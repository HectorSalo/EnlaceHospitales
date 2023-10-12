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
import com.skysam.enlacehospitales.dataClasses.emergency.Doctor
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.databinding.FragmentConsultBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 03/10/2023.
 */

class ConsultDialog: DialogFragment() {
    private var _binding: FragmentConsultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmergencysViewModel by activityViewModels()
    private lateinit var emergency: Emergency
    private lateinit var buttonPositive: Button
    private lateinit var buttonNegative: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentConsultBinding.inflate(layoutInflater)

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
                binding.etInformation.setText(it.secondDoctor?.information)
                binding.etSpecality.setText(it.secondDoctor?.speciality)
                binding.etName.setText(it.secondDoctor?.name)
                binding.etContact.setText(it.secondDoctor?.methodContact)
            }
        }
    }

    private fun validateData() {
        val name = binding.etName.text.toString()
        if (name.isEmpty()) {
            binding.tfName.error = getString(R.string.error_field_empty)
            binding.etName.requestFocus()
            return
        }

        val speciality = binding.etSpecality.text.toString()
        if (speciality.isEmpty()) {
            binding.tfSpeciality.error = getString(R.string.error_field_empty)
            binding.etSpecality.requestFocus()
            return
        }

        Utils.close(binding.root)
        val doctor = Doctor(
                name,
                speciality,
                binding.etContact.text.toString().ifEmpty { "" },
                binding.etInformation.text.toString().ifEmpty { "" },
            )
        viewModel.updateConsult(emergency, doctor)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}