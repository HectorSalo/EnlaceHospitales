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
import com.skysam.enlacehospitales.databinding.FragmentFirstNewHlcBinding
import com.skysam.enlacehospitales.ui.common.DatePicker
import com.skysam.enlacehospitales.ui.common.ExitDialog
import com.skysam.enlacehospitales.ui.common.OnClickDateTime
import com.skysam.enlacehospitales.ui.common.OnClickExit
import com.skysam.enlacehospitales.ui.common.TimePicker
import java.util.Calendar
import java.util.Date

class FirstFragment : Fragment(), OnClickDateTime, OnClickExit {

    private var _binding: FragmentFirstNewHlcBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()
    private lateinit var dateSelected: Date

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

        binding.etDate.setOnClickListener {
            val datePicker = DatePicker(requireActivity(), this)
            datePicker.viewCalendar()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickExit() {
        requireActivity().finish()
    }

    override fun dateSelected(date: Date) {
        dateSelected = date
        val timePicker = TimePicker(requireActivity(), this)
        timePicker.viewTimer()
    }

    override fun timeSelected(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.time = dateSelected
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        dateSelected = calendar.time
        binding.etDate.setText(Utils.convertDateTimeToString(dateSelected))
    }
}