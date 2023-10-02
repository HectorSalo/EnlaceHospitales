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
import com.skysam.enlacehospitales.dataClasses.emergency.TransferPatient
import com.skysam.enlacehospitales.databinding.FragmentTransferBinding

class TransferFragment : Fragment() {

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.goStep(9)
            findNavController().navigate(R.id.action_transferFragment_to_consultFragment)
        }

        binding.btnNext.setOnClickListener { saveData() }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.transfer.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it != null) {
                    binding.etHospital.setText(it.nameHospital)
                    binding.etContact.setText(it.phoneHospital)
                    binding.etDoctor.setText(it.nameDoctor)
                    binding.etInformation.setText(it.information)
                    binding.cbPlans.isChecked = it.isPlansConfirmed
                    binding.cbContactedSection.isChecked = it.isContactedInformationHospitals
                }
            }
        }
    }

    private fun saveData() {
        Utils.close(binding.root)
        val transferPatient = if (binding.etHospital.text.toString().isNotEmpty())
            TransferPatient(
            binding.cbPlans.isChecked,
            binding.cbContactedSection.isChecked,
            binding.etHospital.text.toString().ifEmpty { "" },
            binding.etDoctor.text.toString().ifEmpty { "" },
            binding.etContact.text.toString().ifEmpty { "" },
            binding.etInformation.text.toString().ifEmpty { "" },
        ) else null
        viewModel.setTransfer(transferPatient)
        viewModel.goStep(11)
        findNavController().navigate(R.id.action_transferFragment_to_resultsFragment)
    }
}