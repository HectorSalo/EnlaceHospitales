package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.databinding.FragmentFirstNewHlcBinding
import com.skysam.enlacehospitales.ui.common.DatePicker
import com.skysam.enlacehospitales.ui.common.ExitDialog
import com.skysam.enlacehospitales.ui.common.OnClickDateTime
import com.skysam.enlacehospitales.ui.common.OnClickExit
import com.skysam.enlacehospitales.ui.common.TimePicker
import java.util.Calendar
import java.util.Date

class NotificationFragment : Fragment(), OnClickDateTime, OnClickExit {

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

        dateSelected = Date()
        binding.etDate.setText(Utils.convertDateTimeToString(dateSelected))
        binding.etName.doAfterTextChanged { binding.tfName.error = null }
        binding.etRelationship.doAfterTextChanged { binding.tfRelationship.error = null }
        binding.etInformation.doAfterTextChanged { binding.tfInformation.error = null }
        binding.btnExit.setOnClickListener {
            val exitDialog = ExitDialog(this)
            exitDialog.show(requireActivity().supportFragmentManager, tag)
        }

        binding.btnNext.setOnClickListener { validateData() }

        binding.etDate.setOnClickListener {
            val datePicker = DatePicker(requireActivity(), this)
            datePicker.viewCalendar()
        }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.notification.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it != null) {
                    binding.etName.setText(it.personCall)
                    binding.etRelationship.setText(it.relationshipPatient)
                    binding.etInformation.setText(it.infoPersonCall)
                    binding.etDate.setText(Utils.convertDateTimeToString(it.dateCall))
                    dateSelected = it.dateCall
                    binding.checkBox.isChecked = it.isNeedHelp
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
        val relationship = binding.etRelationship.text.toString()
        if (name.isEmpty()) {
            binding.tfRelationship.error = getString(R.string.error_field_empty)
            binding.etRelationship.requestFocus()
            return
        }
        val information = binding.etInformation.text.toString()
        if (information.isEmpty()) {
            binding.tfInformation.error = getString(R.string.error_field_empty)
            binding.etInformation.requestFocus()
            return
        }

        val notification = Notification(
            dateSelected,
            name,
            relationship,
            information,
            binding.checkBox.isChecked
        )
        viewModel.goStep(1)
        viewModel.setNotification(notification)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
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