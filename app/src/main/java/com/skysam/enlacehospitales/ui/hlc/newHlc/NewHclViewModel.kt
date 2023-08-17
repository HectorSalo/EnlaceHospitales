package com.skysam.enlacehospitales.ui.hlc.newHlc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.repositories.Emergencys

/**
 * Created by Hector Chirinos on 19/06/2023.
 */

class NewHclViewModel: ViewModel() {
 private val _step = MutableLiveData<Int>().apply { value = 0 }
 val step: LiveData<Int> get() = _step

 private val _notification = MutableLiveData<Notification?>().apply { value = null }
 val notification: LiveData<Notification?> get() = _notification

 private val _patient = MutableLiveData<Patient?>().apply { value = null }
 val patient: LiveData<Patient?> get() = _patient

 private val _hospital = MutableLiveData<Hospital?>().apply { value = null }
 val hospital: LiveData<Hospital?> get() = _hospital

 fun goStep(step: Int) {
  _step.value = step
 }

 fun setNotification(notification: Notification) {
  _notification.value = notification
 }
 fun setPatient(patient: Patient) {
  _patient.value = patient
 }

 fun setHospital(hospital: Hospital) {
  _hospital.value = hospital
 }

 fun saveEmergency(emergency: Emergency) {
  Emergencys.saveEmergency(emergency)
 }
}