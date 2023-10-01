package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.Doctor
import com.skysam.enlacehospitales.databinding.FragmentConsultBinding

class ConsultFragment : Fragment() {

    private var _binding: FragmentConsultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConsultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.goStep(8)
            findNavController().navigate(R.id.action_consultFragment_to_articlesFragment)
        }

        binding.btnNext.setOnClickListener { saveData() }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.secondDoctor.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it != null) {
                    binding.etName.setText(it.name)
                    binding.etContact.setText(it.methodContact)
                    binding.etSpecality.setText(it.speciality)
                    binding.etInformation.setText(it.information)
                }
            }
        }
    }

    private fun saveData() {
        Utils.close(binding.root)
        val doctor = Doctor(
            binding.etName.text.toString().ifEmpty { "" },
            binding.etSpecality.text.toString().ifEmpty { "" },
            binding.etContact.text.toString().ifEmpty { "" },
            binding.etInformation.text.toString().ifEmpty { "" },
        )
        viewModel.setSecondDoctor(doctor)
        viewModel.goStep(10)
        findNavController().navigate(R.id.action_consultFragment_to_transferFragment)
    }
}