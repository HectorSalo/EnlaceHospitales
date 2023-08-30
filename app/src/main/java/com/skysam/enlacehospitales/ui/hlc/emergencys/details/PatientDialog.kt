package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.databinding.DialogPatientDetailsBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel

/**
 * Created by Hector Chirinos on 29/08/2023.
 */

class PatientDialog: DialogFragment() {
    private var _binding: DialogPatientDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmergencysViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogPatientDetailsBinding.inflate(layoutInflater)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.etName.focusable = View.NOT_FOCUSABLE
            binding.etCongregation.focusable = View.NOT_FOCUSABLE
            binding.etPhone.focusable = View.NOT_FOCUSABLE
            binding.etAge.focusable = View.NOT_FOCUSABLE
            binding.etComments.focusable = View.NOT_FOCUSABLE
            binding.etNameFather.focusable = View.NOT_FOCUSABLE
            binding.etNameMother.focusable = View.NOT_FOCUSABLE
            binding.etCommentsChild.focusable = View.NOT_FOCUSABLE
            binding.etAgeBorn.focusable = View.NOT_FOCUSABLE
            binding.etApgarBorn.focusable = View.NOT_FOCUSABLE
            binding.etApgarMinutes.focusable = View.NOT_FOCUSABLE
            binding.etDate.focusable = View.NOT_FOCUSABLE
            binding.etWeight.focusable = View.NOT_FOCUSABLE
        } else {
            binding.etName.isEnabled = false
            binding.etCongregation.isEnabled = false
            binding.etPhone.isEnabled = false
            binding.etAge.isEnabled = false
            binding.etComments.isEnabled = false
            binding.etNameFather.isEnabled = false
            binding.etNameMother.isEnabled = false
            binding.etCommentsChild.isEnabled = false
            binding.etAgeBorn.isEnabled = false
            binding.etApgarBorn.isEnabled = false
            binding.etApgarMinutes.isEnabled = false
            binding.etDate.isEnabled =false
            binding.etWeight.isEnabled = false
        }
        binding.etDate.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
        binding.cbBaptized.isClickable = false
        binding.cbReputation.isClickable = false
        binding.cbDpa.isClickable = false
        binding.cbChild.isClickable = false
        binding.cbBaptizedFather.isClickable = false
        binding.cbBaptizedMother.isClickable = false
        binding.cbBorn.isClickable = false
        binding.rbFemale.isClickable = false
        binding.rbMale.isClickable = false

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
        viewModel.patientToView.observe(this.requireActivity()) {
            if (_binding != null) {
                binding.etName.setText(it.name)
                binding.etCongregation.setText(it.congregation)
                binding.etPhone.setText(it.phone)
                binding.etAge.setText(it.age.toString())
                binding.cbBaptized.isChecked = it.isBaptized
                binding.cbReputation.isChecked = it.isReputation
                binding.cbDpa.isChecked = it.isDpaComplete
                if (it.gender == Constants.MALE) binding.rbMale.isChecked = true
                else binding.rbFemale.isChecked = true
                binding.etComments.setText(it.comments)
                binding.cbChild.isChecked = it.childPatient != null

                if (it.childPatient != null) {
                    binding.constraintChild.visibility = View.VISIBLE
                    binding.etNameFather.setText(it.childPatient?.nameFather)
                    binding.etNameMother.setText(it.childPatient?.nameMother)
                    binding.etCommentsChild.setText(it.childPatient?.comments)
                    binding.cbBaptizedFather.isChecked = it.childPatient!!.isFatherBaptized
                    binding.cbBaptizedMother.isChecked = it.childPatient!!.isMotherBaptized
                    binding.cbBorn.isChecked = it.childPatient?.bornPatient != null

                    if (it.childPatient?.bornPatient != null) {
                        binding.constraintBorn.visibility = View.VISIBLE
                        binding.etWeight.setText(Utils.convertDoubleToString(it.childPatient?.bornPatient!!.weight))
                        binding.etAgeBorn.setText(it.childPatient?.bornPatient!!.weeksAge)
                        binding.etDate.setText(Utils.convertDateToString(it.childPatient?.bornPatient!!.dateBorn))
                        binding.etApgarBorn.setText(Utils.convertDoubleToString(it.childPatient?.bornPatient!!.bornAPGAR))
                        binding.etApgarMinutes.setText(Utils.convertDoubleToString(it.childPatient?.bornPatient!!.fiveMinutesAPGAR))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}