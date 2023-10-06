package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.databinding.DialogNotificationDetailsBinding
import com.skysam.enlacehospitales.ui.common.DatePicker
import com.skysam.enlacehospitales.ui.common.OnClickDateTime
import com.skysam.enlacehospitales.ui.common.TimePicker
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel
import java.util.Calendar
import java.util.Date

/**
 * Created by Hector Chirinos on 29/08/2023.
 */

class NotificationDialog: DialogFragment(), OnClickDateTime {
 private var _binding: DialogNotificationDetailsBinding? = null
 private val binding get() = _binding!!
 private val viewModel: EmergencysViewModel by activityViewModels()
 private lateinit var emergency: Emergency
 private lateinit var buttonPositive: Button
 private lateinit var buttonNegative: Button
 private lateinit var dateSelected: Date

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
  _binding = DialogNotificationDetailsBinding.inflate(layoutInflater)

  val builder = AlertDialog.Builder(requireActivity())
  builder.setTitle(getString(R.string.text_details))
   .setView(binding.root)
   .setPositiveButton(R.string.text_accept, null)
   .setNegativeButton(R.string.text_cancel, null)

  val dialog = builder.create()
  dialog.show()

  buttonPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
  buttonNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)

  subscribeViewModel()

  return dialog
 }



 private fun subscribeViewModel() {
  viewModel.isView.observe(this.requireActivity()) {
   if (_binding != null) {
    if (it) {
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      binding.etName.focusable = View.NOT_FOCUSABLE
      binding.etRelationship.focusable = View.NOT_FOCUSABLE
      binding.etDate.focusable = View.NOT_FOCUSABLE
      binding.etInformation.focusable = View.NOT_FOCUSABLE
     } else {
      binding.etName.isEnabled = false
      binding.etRelationship.isEnabled = false
      binding.etDate.isEnabled = false
      binding.etInformation.isEnabled = false
     }
     buttonNegative.visibility = View.GONE
     binding.checkBox.isClickable = false
     binding.etDate.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
    } else{
     binding.etDate.setOnClickListener {
      val datePicker = DatePicker(requireActivity(), this)
      datePicker.viewCalendar()
     }
     buttonPositive.setText(R.string.text_update)
     buttonPositive.setOnClickListener { validateData() }
    }
   }
  }
  viewModel.emergencyToView.observe(this.requireActivity()) {
   if (_binding != null) {
    emergency = it
    binding.etName.setText(it.notification?.personCall)
    binding.etRelationship.setText(it.notification?.relationshipPatient)
    binding.etDate.setText(Utils.convertDateTimeToString(it.notification!!.dateCall))
    binding.etInformation.setText(it.notification.infoPersonCall)
    binding.checkBox.isChecked = it.notification.isNeedHelp
    dateSelected = it.notification.dateCall
   }
  }
 }

 override fun onDestroyView() {
  super.onDestroyView()
  _binding = null
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

  viewModel.updateNotification(emergency, notification)
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
}