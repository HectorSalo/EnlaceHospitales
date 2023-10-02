package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentFourthNewHclBinding

class IssueFragment : Fragment() {

    private var _binding: FragmentFourthNewHclBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()

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
            viewModel.speciality.observe(viewLifecycleOwner) {
                if (_binding != null) {
                    if (it != null) {
                        binding.etSpeciality.setText(it)
                    }
                }
            }
            viewModel.issueMedical.observe(viewLifecycleOwner) {
                if (_binding != null) {
                    if (it != null) {
                        binding.etInformation.setText(it)
                    }
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

        viewModel.setIssue(information)
        viewModel.setSpeciality(binding.etSpeciality.text.toString().ifEmpty { "" })
        viewModel.goStep(4)
        findNavController().navigate(R.id.action_fourthFragment_to_labFragment)
    }
}