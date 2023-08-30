package com.skysam.enlacehospitales.ui.hlc.emergencys

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.repositories.Emergencys

class EmergencysViewModel : ViewModel() {
    val emergencys: LiveData<List<Emergency>> = Emergencys.getEmergencys().asLiveData()

    private val _notificationToView = MutableLiveData<Notification>()
    val notificationToView: LiveData<Notification> get() = _notificationToView

    private val _patientToView = MutableLiveData<Patient>()
    val patientToView: LiveData<Patient> get() = _patientToView

    private val _hospitalToView = MutableLiveData<Hospital>()
    val hospitalToView: LiveData<Hospital> get() = _hospitalToView

    private val _issueMedicalToView = MutableLiveData<String>()
    val issueMedicalToView: LiveData<String> get() = _issueMedicalToView


    fun viewNotification(notification: Notification) {
        _notificationToView.value = notification
    }
    fun viewPatient(patient: Patient) {
        _patientToView.value = patient
    }
    fun viewHospital(hospital: Hospital) {
        _hospitalToView.value = hospital
    }
    fun viewIssueMedical(issue: String) {
        _issueMedicalToView.value = issue
    }

    fun finish(emergency: Emergency) {
        Emergencys.finishEmergency(emergency)
    }
}