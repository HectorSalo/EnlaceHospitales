package com.skysam.enlacehospitales.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.databinding.FragmentHomeBinding
import com.skysam.enlacehospitales.ui.common.OnClickDateTime
import com.skysam.enlacehospitales.ui.common.TimePicker
import com.skysam.enlacehospitales.ui.hlc.newHlc.NewHlcActivity
import java.util.Calendar
import java.util.Date

class HomeFragment : Fragment(), OnClickDateTime {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var dateSelected: Date

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            startActivity(Intent(requireContext(), NewHlcActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun dateSelected(date: Date) {
        dateSelected = date
        val timePicker = TimePicker(requireActivity(), this)
        timePicker.viewTimer()
    }

    override fun timeSelected(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar .time = dateSelected
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        dateSelected = calendar.time
        binding.textHome.text = Utils.convertDateTimeToString(dateSelected)
    }
}