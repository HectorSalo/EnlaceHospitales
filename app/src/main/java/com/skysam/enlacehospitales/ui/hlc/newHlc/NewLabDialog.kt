package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.databinding.DialogNewLabBinding
import com.skysam.enlacehospitales.ui.common.DatePicker
import com.skysam.enlacehospitales.ui.common.OnClickDateTime
import com.skysam.enlacehospitales.ui.common.TimePicker
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NewLabDialog(private val fromEmergency: Boolean): DialogFragment(), OnClickDateTime,
    TextWatcher {
    private var _binding: DialogNewLabBinding? = null
    private val binding get() = _binding!!
    private val viewModelHcl: NewHclViewModel by activityViewModels()
    private val viewModelEmergency: EmergencysViewModel by activityViewModels()
    private lateinit var dateSelected: Date
    private lateinit var emergency: Emergency

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogNewLabBinding.inflate(layoutInflater)

        dateSelected = Date()
        binding.etDate.setText(Utils.convertDateTimeToString(dateSelected))
        binding.etHemoglobina.addTextChangedListener(this)
        binding.etHematocrito.addTextChangedListener(this)
        binding.etPlaquetas.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                var cadena = s.toString()
                cadena = cadena.replace(",", "").replace(".", "")
                val cantidad: Double = cadena.toDouble() / 1000
                cadena = String.format(Locale.GERMANY, "%,.3f", cantidad)

                if (s.toString() == binding.etPlaquetas.text.toString()) {
                    binding.etPlaquetas.removeTextChangedListener(this)
                    binding.etPlaquetas.setText(cadena)
                    binding.etPlaquetas.setSelection(cadena.length)
                    binding.etPlaquetas.addTextChangedListener(this)
                }
            }
        })

        binding.etDate.setOnClickListener {
            val datePicker = DatePicker(requireActivity(), this)
            datePicker.viewCalendar()
        }

        subscribeViewModel()

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.text_new_lab))
            .setView(binding.root)
            .setPositiveButton(R.string.text_save) { _, _ ->
                sendData()
            }
            .setNegativeButton(R.string.text_cancel, null)

        val dialog = builder.create()
        dialog.show()

        return dialog
    }



    private fun subscribeViewModel() {
        viewModelEmergency.emergencyNewLab.observe(this.requireActivity()) {
            if (_binding != null) {
                emergency = it
            }
        }
    }

    private fun sendData() {
        val lab = AnalisysLab(
            dateSelected,
            Utils.convertStringToDouble(binding.etHemoglobina.text.toString()),
            Utils.convertStringToDouble(binding.etPlaquetas.text.toString()),
            Utils.convertStringToDouble(binding.etHematocrito.text.toString()),
            binding.etOthers.text.toString()
        )

        if (!fromEmergency) viewModelHcl.setLabs(lab) else viewModelEmergency.saveNewLab(emergency, lab)
        dismiss()
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

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(s: Editable?) {
        var cadena = s.toString()
        cadena = cadena.replace(",", "").replace(".", "")
        val cantidad: Double = cadena.toDouble() / 100
        cadena = String.format(Locale.GERMANY, "%,.2f", cantidad)

        if (s.toString() == binding.etHemoglobina.text.toString()) {
            binding.etHemoglobina.removeTextChangedListener(this)
            binding.etHemoglobina.setText(cadena)
            binding.etHemoglobina.setSelection(cadena.length)
            binding.etHemoglobina.addTextChangedListener(this)
        }
        if (s.toString() == binding.etHematocrito.text.toString()) {
            binding.etHematocrito.removeTextChangedListener(this)
            binding.etHematocrito.setText(cadena)
            binding.etHematocrito.setSelection(cadena.length)
            binding.etHematocrito.addTextChangedListener(this)
        }
    }
}