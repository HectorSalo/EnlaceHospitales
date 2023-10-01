package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentTreatmentBinding

class TreatmentFragment : Fragment() {

    private var _binding: FragmentTreatmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTreatmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.goStep(5)
            findNavController().navigate(R.id.action_treatmentFragment_to_doctorFragment)
        }

        binding.btnNext.setOnClickListener {
            viewModel.goStep(7)
            findNavController().navigate(R.id.action_labFragment_to_fourthFragment)
        }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.treatment.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it != null) {
                    binding.etTreatment.setText(it.information)
                    binding.cbHelp.isChecked = it.isCommunicatedWithDoctors
                }
            }
        }
    }

}