package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentDoctorBinding

class DoctorFragment : Fragment() {

    private var _binding: FragmentDoctorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()
    private lateinit var doctorAdapater: DoctorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doctorAdapater = DoctorAdapter()
        binding.rvDoctor.apply {
            setHasFixedSize(true)
            adapter = doctorAdapater
        }

        binding.fab.setOnClickListener {
            val newDoctorDialog = NewDoctorDialog(false)
            newDoctorDialog.show(requireActivity().supportFragmentManager, tag)
        }

        binding.btnBack.setOnClickListener {
            viewModel.goStep(4)
            findNavController().navigate(R.id.action_doctorFragment_to_labFragment)
        }

        binding.btnNext.setOnClickListener {
            viewModel.goStep(6)
            findNavController().navigate(R.id.action_doctorFragment_to_treatmentFragment)
        }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.doctors.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it.isNotEmpty()) {
                    binding.rvDoctor.visibility = View.VISIBLE
                    binding.tvListEmpty.visibility = View.GONE
                    doctorAdapater.updateList(it)
                }
            }
        }
    }

}