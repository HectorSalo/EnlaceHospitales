package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentFourthNewHclBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FourthFragment : Fragment() {

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

        binding.tfInformation.helperText = getText(R.string.text_helper_problem)

        binding.btnNext.setOnClickListener {
            viewModel.goStep(4)
            lifecycleScope.launch {
                delay(500)
                requireActivity().finish()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}