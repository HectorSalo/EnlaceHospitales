package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.BornPatient
import com.skysam.enlacehospitales.dataClasses.emergency.ChildPatient
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.databinding.DialogPatientDetailsBinding
import com.skysam.enlacehospitales.ui.common.DatePicker
import com.skysam.enlacehospitales.ui.common.OnClickDateTime
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel
import java.util.Date
import java.util.Locale

/**
 * Created by Hector Chirinos on 29/08/2023.
 */

class PatientDialog: DialogFragment(), TextWatcher, OnClickDateTime {
    private var _binding: DialogPatientDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmergencysViewModel by activityViewModels()
    private lateinit var emergency: Emergency
    private lateinit var buttonPositive: Button
    private lateinit var buttonNegative: Button
    private lateinit var dateSelected: Date

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogPatientDetailsBinding.inflate(layoutInflater)

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
                    buttonNegative.visibility = View.GONE
                } else {
                    binding.etName.doAfterTextChanged { binding.tfName.error = null }
                    binding.etPhone.doAfterTextChanged { binding.tfPhone.error = null }
                    binding.etApgarBorn.addTextChangedListener(this)
                    binding.etApgarMinutes.addTextChangedListener(this)
                    binding.etWeight.addTextChangedListener(this)

                    binding.cbChild.setOnCheckedChangeListener { _, isChecked ->
                        binding.constraintChild.visibility = if (isChecked) View.VISIBLE else View.GONE
                    }
                    binding.cbBorn.setOnCheckedChangeListener { _, isChecked ->
                        dateSelected = Date()
                        binding.constraintBorn.visibility = if (isChecked) View.VISIBLE else View.GONE
                    }
                    binding.etDate.setOnClickListener {
                        val datePicker = DatePicker(requireActivity(), this)
                        datePicker.viewCalendar()
                    }

                    buttonPositive.setText(R.string.text_update)
                    buttonPositive.setOnClickListener { validateData() }
                }
            }
        }
        viewModel.emergencyToView.observe(this.requireActivity()) {
            if (_binding != null) {
                emergency = it
                binding.etName.setText(it.patient?.name)
                binding.etCongregation.setText(it.patient?.congregation)
                binding.etPhone.setText(it.patient?.phone)
                binding.etAge.setText(it.patient?.age.toString())
                binding.cbBaptized.isChecked = it.patient!!.isBaptized
                binding.cbReputation.isChecked = it.patient!!.isReputation
                binding.cbDpa.isChecked = it.patient!!.isDpaComplete
                if (it.patient!!.gender == Constants.MALE) binding.rbMale.isChecked = true
                else binding.rbFemale.isChecked = true
                binding.etComments.setText(it.patient?.comments)
                binding.cbChild.isChecked = it.patient?.childPatient != null

                if (it.patient?.childPatient != null) {
                    binding.constraintChild.visibility = View.VISIBLE
                    binding.etNameFather.setText(it.patient?.childPatient?.nameFather)
                    binding.etNameMother.setText(it.patient?.childPatient?.nameMother)
                    binding.etCommentsChild.setText(it.patient?.childPatient?.comments)
                    binding.cbBaptizedFather.isChecked = it.patient?.childPatient!!.isFatherBaptized
                    binding.cbBaptizedMother.isChecked = it.patient?.childPatient!!.isMotherBaptized
                    binding.cbBorn.isChecked = it.patient?.childPatient?.bornPatient != null

                    if (it.patient?.childPatient?.bornPatient != null) {
                        binding.constraintBorn.visibility = View.VISIBLE
                        binding.etWeight.setText(Utils.convertDoubleToString(it.patient?.childPatient?.bornPatient!!.weight))
                        binding.etAgeBorn.setText(it.patient?.childPatient?.bornPatient!!.weeksAge.toString())
                        binding.etDate.setText(Utils.convertDateToString(it.patient?.childPatient?.bornPatient!!.dateBorn))
                        binding.etApgarBorn.setText(Utils.convertDoubleToString(it.patient?.childPatient?.bornPatient!!.bornAPGAR))
                        binding.etApgarMinutes.setText(Utils.convertDoubleToString(it.patient?.childPatient?.bornPatient!!.fiveMinutesAPGAR))
                    }
                }
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
        val phone = binding.etPhone.text.toString()
        if (phone.isEmpty()) {
            binding.tfPhone.error = getString(R.string.error_field_empty)
            binding.etPhone.requestFocus()
            return
        }

        val bornPatient = if (binding.cbBorn.isChecked) {
            BornPatient(
                Utils.convertStringToDouble(binding.etWeight.text.toString()),
                if (binding.etAgeBorn.text.toString().isNotEmpty()) binding.etAgeBorn.text.toString().toInt() else 0,
                dateSelected,
                Utils.convertStringToDouble(binding.etApgarBorn.text.toString()),
                Utils.convertStringToDouble(binding.etApgarMinutes.text.toString())
            )
        } else null

        val childPatient = if (binding.cbChild.isChecked) {
            ChildPatient(
                binding.etNameFather.text.toString(),
                binding.etNameMother.text.toString(),
                binding.cbBaptizedFather.isChecked,
                binding.cbBaptizedMother.isChecked,
                binding.etCommentsChild.text.toString(),
                bornPatient
            )
        } else null

        val patient = Patient(
            name,
            if (binding.rbMale.isChecked) Constants.MALE else Constants.FEMALE,
            if (binding.etAge.text.toString().isEmpty()) 0 else binding.etAge.text.toString().toInt(),
            phone,
            binding.etComments.text.toString(),
            binding.cbBaptized.isChecked,
            binding.cbReputation.isChecked,
            binding.cbDpa.isChecked,
            binding.etCongregation.text.toString(),
            childPatient
        )
        viewModel.updatePatient(emergency, patient)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(s: Editable?) {
        var cadena = s.toString()
        cadena = cadena.replace(",", "").replace(".", "")
        val cantidad: Double = cadena.toDouble() / 100
        cadena = String.format(Locale.GERMANY, "%,.2f", cantidad)

        if (s.toString() == binding.etApgarBorn.text.toString()) {
            binding.etApgarBorn.removeTextChangedListener(this)
            binding.etApgarBorn.setText(cadena)
            binding.etApgarBorn.setSelection(cadena.length)
            binding.etApgarBorn.addTextChangedListener(this)
        }
        if (s.toString() == binding.etApgarMinutes.text.toString()) {
            binding.etApgarMinutes.removeTextChangedListener(this)
            binding.etApgarMinutes.setText(cadena)
            binding.etApgarMinutes.setSelection(cadena.length)
            binding.etApgarMinutes.addTextChangedListener(this)
        }
        if (s.toString() == binding.etWeight.text.toString()) {
            binding.etWeight.removeTextChangedListener(this)
            binding.etWeight.setText(cadena)
            binding.etWeight.setSelection(cadena.length)
            binding.etWeight.addTextChangedListener(this)
        }
    }

    override fun dateSelected(date: Date) {
        dateSelected = date
        binding.etDate.setText(Utils.convertDateToString(dateSelected))
    }

    override fun timeSelected(hour: Int, minute: Int) {}
}