package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.BornPatient
import com.skysam.enlacehospitales.dataClasses.emergency.ChildPatient
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.databinding.FragmentSecondNewHlcBinding
import com.skysam.enlacehospitales.ui.common.DatePicker
import com.skysam.enlacehospitales.ui.common.OnClickDateTime
import java.util.Date

class SecondFragment : Fragment(), OnClickDateTime {

    private var _binding: FragmentSecondNewHlcBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()
    private lateinit var dateSelected: Date

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondNewHlcBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.doAfterTextChanged { binding.tfName.error = null }
        binding.etAge.doAfterTextChanged { binding.tfAge.error = null }

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
        binding.btnNext.setOnClickListener { validateData() }
        binding.btnBack.setOnClickListener {
            viewModel.goStep(0)
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.patient.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it != null) {
                    binding.etName.setText(it.name)
                    binding.etCongregation.setText(it.congregation)
                    binding.cbBaptized.isChecked = it.isBaptized
                    binding.cbReputation.isChecked = it.isReputation
                    binding.cbDpa.isChecked = it.isDpaComplete
                    if (it.gender == Constants.MALE) binding.rbMale.isChecked = true
                    else binding.rbFemale.isChecked = true
                    binding.etAge.setText(it.age.toString())
                    binding.etComments.setText(it.comments)

                    if (it.childPatient != null) {
                        binding.cbChild.isChecked = true
                        binding.etNameFather.setText(it.childPatient?.nameFather)
                        binding.etNameMother.setText(it.childPatient?.nameMother)
                        binding.cbBaptizedFather.isChecked = it.childPatient!!.isFatherBaptized
                        binding.cbBaptizedMother.isChecked = it.childPatient!!.isMotherBaptized
                        binding.etCommentsChild.setText(it.childPatient?.comments)

                        if (it.childPatient?.bornPatient != null) {
                            binding.cbBorn.isChecked = true
                            binding.etWeight.setText(it.childPatient?.bornPatient?.weight.toString())
                            binding.etAgeBorn.setText(it.childPatient?.bornPatient?.weeksAge.toString())
                            binding.etApgarBorn.setText(it.childPatient?.bornPatient?.bornAPGAR.toString())
                            binding.etApgarMinutes.setText(it.childPatient?.bornPatient?.fiveMinutesAPGAR.toString())
                            binding.etDate.setText(Utils.convertDateToString(it.childPatient?.bornPatient!!.dateBorn))
                        }
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
        val age = binding.etAge.text.toString()
        if (age.isEmpty()) {
            binding.tfAge.error = getString(R.string.error_field_empty)
            binding.etAge.requestFocus()
            return
        }

        val bornPatient = if (binding.cbBorn.isChecked) {
            BornPatient(
                if (binding.etWeight.text.toString().isNotEmpty()) binding.etWeight.text.toString().toDouble() else 0.0,
                if (binding.etAgeBorn.text.toString().isNotEmpty()) binding.etAgeBorn.text.toString().toInt() else 0,
                dateSelected,
                if (binding.etApgarBorn.text.toString().isNotEmpty()) binding.etApgarBorn.text.toString().toDouble() else 0.0,
                if (binding.etApgarMinutes.text.toString().isNotEmpty()) binding.etApgarMinutes.text.toString().toDouble() else 0.0,
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
            age.toInt(),
            binding.etComments.text.toString(),
            binding.cbBaptized.isChecked,
            binding.cbReputation.isChecked,
            binding.cbDpa.isChecked,
            binding.etCongregation.text.toString(),
            childPatient
        )

        viewModel.goStep(2)
        viewModel.setPatient(patient)
        findNavController().navigate(R.id.action_SecondFragment_to_thirdFragment)
    }

    override fun dateSelected(date: Date) {
        dateSelected = date
        binding.etDate.setText(Utils.convertDateToString(dateSelected))
    }

    override fun timeSelected(hour: Int, minute: Int) {

    }
}