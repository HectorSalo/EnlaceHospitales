package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentLabBinding

class LabFragment : Fragment() {

    private var _binding: FragmentLabBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()
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
}