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
import com.skysam.enlacehospitales.databinding.FragmentStrategiesBinding

class StrategiesFragment : Fragment() {

    private var _binding: FragmentStrategiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStrategiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.goStep(6)
            findNavController().navigate(R.id.action_strategiesFragment_to_treatmentFragment)
        }

        binding.btnNext.setOnClickListener { saveData() }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.strategies.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it != null) {
                    binding.etStrategy.setText(it)
                }
            }
        }
    }

    private fun saveData() {
        Utils.close(binding.root)
        viewModel.setStrategies(binding.etStrategy.text.toString().ifEmpty { "" })
        viewModel.goStep(8)
        findNavController().navigate(R.id.action_strategiesFragment_to_articlesFragment)
    }
}