package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab
import com.skysam.enlacehospitales.dataClasses.emergency.ArticlesMedical
import com.skysam.enlacehospitales.dataClasses.emergency.Doctor
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.dataClasses.emergency.Tracing
import com.skysam.enlacehospitales.dataClasses.emergency.TransferPatient
import com.skysam.enlacehospitales.dataClasses.emergency.Treatment
import com.skysam.enlacehospitales.databinding.FragmentResultsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date


class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()
    private lateinit var patient: Patient
    private lateinit var notification: Notification
    private lateinit var hospital: Hospital
    private lateinit var issueMedical: String
    private lateinit var speciality: String
    private var labs = listOf<AnalisysLab>()
    private var doctors = listOf<Doctor>()
    private var treatment: Treatment? = null
    private lateinit var strategies: String
    private var articlesMedical: ArticlesMedical? = null
    private var secondDoctor: Doctor? = null
    private var transferPatient: TransferPatient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.goStep(10)
            findNavController().navigate(R.id.action_resultsFragment_to_transferFragment)
        }

        binding.btnNext.setOnClickListener { saveData() }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.notification.observe(viewLifecycleOwner) {
                if (_binding != null) notification = it!!
            }
        viewModel.patient.observe(viewLifecycleOwner) {
            if (_binding != null) patient = it!!
        }
        viewModel.hospital.observe(viewLifecycleOwner) {
            if (_binding != null) hospital = it!!
        }
        viewModel.issueMedical.observe(viewLifecycleOwner) {
            if (_binding != null) issueMedical = it!!
        }
        viewModel.speciality.observe(viewLifecycleOwner) {
            if (_binding != null) speciality = it!!
        }
        viewModel.labs.observe(viewLifecycleOwner) {
            if (_binding != null) labs = it!!
        }
        viewModel.doctors.observe(viewLifecycleOwner) {
            if (_binding != null) doctors = it!!
        }
        viewModel.treatment.observe(viewLifecycleOwner) {
            if (_binding != null) treatment = it
        }
        viewModel.strategies.observe(viewLifecycleOwner) {
            if (_binding != null) strategies = it!!
        }
        viewModel.articles.observe(viewLifecycleOwner) {
            if (_binding != null) articlesMedical = it
        }
        viewModel.secondDoctor.observe(viewLifecycleOwner) {
            if (_binding != null) secondDoctor = it
        }
        viewModel.transfer.observe(viewLifecycleOwner) {
            if (_binding != null) transferPatient = it
        }
    }

    private fun saveData() {
        Utils.close(binding.root)
        val tracing = if (binding.etResults.text.toString().isNotEmpty())
            Tracing(
            binding.cbOldersContacted.isChecked,
            binding.etResults.text.toString().ifEmpty { "" }
        ) else null

        val emergency = Emergency(
            "",
            Date(),
            Date(),
            true,
            speciality,
            notification,
            patient,
            hospital,
            issueMedical,
            labs,
            doctors,
            treatment,
            strategies,
            articlesMedical,
            secondDoctor,
            transferPatient,
            tracing
        )

        viewModel.saveEmergency(emergency)
        lifecycleScope.launch {
            viewModel.goStep(12)
            delay(500)
            requireActivity().finish()
        }
    }
}