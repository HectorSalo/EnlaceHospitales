package com.skysam.enlacehospitales.ui.hlc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentFirstNewHlcBinding
import com.skysam.enlacehospitales.ui.common.ExitDialog
import com.skysam.enlacehospitales.ui.common.OnClickExit

class FirstFragment : Fragment(), OnClickExit {

    private var _binding: FragmentFirstNewHlcBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstNewHlcBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExit.setOnClickListener {
            val exitDialog = ExitDialog(this)
            exitDialog.show(requireActivity().supportFragmentManager, tag)
        }

        binding.btnNext.setOnClickListener {
            viewModel.goStep(1)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickExit() {
        requireActivity().finish()
    }
}