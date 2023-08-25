package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.databinding.FragmentFourthNewHclBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class FourthFragment : Fragment() {

    private var _binding: FragmentFourthNewHclBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()

    private lateinit var patient: Patient
    private lateinit var notification: Notification
    private lateinit var hospital: Hospital

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourthNewHclBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etInformation.doAfterTextChanged { binding.tfInformation.error = null }

        binding.btnBack.setOnClickListener {
            viewModel.goStep(2)
            findNavController().navigate(R.id.action_fourthFragment_to_thirdFragment)
        }

        binding.btnNext.setOnClickListener { validateData() }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        if (_binding != null) {
            viewModel.notification.observe(viewLifecycleOwner) {
                if (_binding != null) {
                    notification = it!!
                }
            }
            viewModel.patient.observe(viewLifecycleOwner) {
                if (_binding != null) {
                    patient = it!!
                }
            }
            viewModel.hospital.observe(viewLifecycleOwner) {
                if (_binding != null) {
                    hospital = it!!
                }
            }
        }
    }

    private fun validateData() {
        val information = binding.etInformation.text.toString()
        if (information.isEmpty()) {
            binding.tfInformation.error = getString(R.string.error_field_empty)
            binding.etInformation.requestFocus()
            return
        }

        val emergency = Emergency(
            "",
            Date(),
            Date(),
            Constants.IS_ACTIVE,
            "",
            notification,
            patient,
            hospital,
            information,
            listOf(),
            listOf(),
            null,
            "",
            null,
            false,
            null,
            false,
            null,
            null
        )

        viewModel.saveEmergency(emergency)
        viewModel.goStep(4)
        lifecycleScope.launch {
            delay(500)
            requireActivity().finish()
        }
    }
}