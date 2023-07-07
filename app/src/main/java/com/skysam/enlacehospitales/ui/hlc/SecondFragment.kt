package com.skysam.enlacehospitales.ui.hlc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.databinding.FragmentSecondNewHlcBinding
import com.skysam.enlacehospitales.ui.common.DatePicker
import com.skysam.enlacehospitales.ui.common.OnClickDateTime
import java.util.Date

class SecondFragment : Fragment(), OnClickDateTime {

    private var _binding: FragmentSecondNewHlcBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()
    private lateinit var dateSelected: Date

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondNewHlcBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cbChild.setOnCheckedChangeListener { _, isChecked ->
            binding.constraintChild.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        binding.etDate.setOnClickListener {
            val datePicker = DatePicker(requireActivity(), this)
            datePicker.viewCalendar()
        }
        binding.btnNext.setOnClickListener {
            viewModel.goStep(2)
            findNavController().navigate(R.id.action_SecondFragment_to_thirdFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun dateSelected(date: Date) {
        dateSelected = date
        binding.etDate.setText(Utils.convertDateToString(dateSelected))
    }

    override fun timeSelected(hour: Int, minute: Int) {

    }
}