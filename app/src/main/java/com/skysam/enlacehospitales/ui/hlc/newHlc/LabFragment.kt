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
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.databinding.FragmentLabBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class LabFragment : Fragment() {

    private var _binding: FragmentLabBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()
    private lateinit var patient: Patient
    private lateinit var notification: Notification
    private lateinit var hospital: Hospital
    private lateinit var issueMedical: String
    private lateinit var labAdapter: LabAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        labAdapter = LabAdapter()
        binding.rvLab.apply {
            setHasFixedSize(true)
            adapter = labAdapter
        }

        binding.fab.setOnClickListener {
            val newLabDialog = NewLabDialog(false)
            newLabDialog.show(requireActivity().supportFragmentManager, tag)
        }

        binding.btnBack.setOnClickListener {
            viewModel.goStep(3)
            findNavController().navigate(R.id.action_labFragment_to_fourthFragment)
        }

        binding.btnNext.setOnClickListener {
            viewModel.goStep(5)
            findNavController().navigate(R.id.action_labFragment_to_doctorFragment)
        }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        if (_binding != null) {
            /*viewModel.notification.observe(viewLifecycleOwner) {
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
            }*/
            viewModel.labs.observe(viewLifecycleOwner) {
                if (_binding != null) {
                    if (it.isNotEmpty()) {
                        binding.rvLab.visibility = View.VISIBLE
                        binding.tvListEmpty.visibility = View.GONE
                        labAdapter.updateList(it)
                    }
                }
            }
        }
    }

    private fun save() {
        /*val emergency = Emergency(
            "",
            Date(),
            Date(),
            true,
            "",
            notification,
            patient,
            hospital,
            issueMedical,
            labs,
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
        lifecycleScope.launch {
            viewModel.goStep(5)
            delay(500)
            requireActivity().finish()
        }*/
    }

}