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
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.databinding.FragmentThirdNewHclBinding

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdNewHclBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdNewHclBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.doAfterTextChanged { binding.tfName.error = null }

        binding.btnBack.setOnClickListener {
            viewModel.goStep(1)
            findNavController().navigate(R.id.action_thirdFragment_to_SecondFragment)
        }

        binding.btnNext.setOnClickListener { validateData() }

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.hospital.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it != null) {
                    binding.etName.setText(it.nameHospital)
                    binding.etRoom.setText(it.room)
                    binding.etNameOlders.setText(it.namesOldersContacted)
                    binding.etPhoneOlders.setText(it.phonesOldersContacted)
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

        val hospital = Hospital(
            name,
            binding.etRoom.text.toString(),
            binding.etNameOlders.text.toString(),
            binding.etPhoneOlders.text.toString()
        )

        viewModel.setHospital(hospital)
        viewModel.goStep(3)
        findNavController().navigate(R.id.action_thirdFragment_to_fourthFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}