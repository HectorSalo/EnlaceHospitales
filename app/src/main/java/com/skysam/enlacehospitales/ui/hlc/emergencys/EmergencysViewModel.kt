package com.skysam.enlacehospitales.ui.hlc.emergencys

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab
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

    private val _labToView = MutableLiveData<List<AnalisysLab>>()
    val labToView: LiveData<List<AnalisysLab>> get() = _labToView

    private val _newLab = MutableLiveData<Boolean>()
    val newLab: LiveData<Boolean> get() = _newLab

    private val _emergencyNewLab = MutableLiveData<Emergency>()
    val emergencyNewLab: LiveData<Emergency> get() = _emergencyNewLab


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
    fun viewLab(labs: List<AnalisysLab>) {
        _labToView.value = labs
    }
    fun newLab(isNew: Boolean) {
        _newLab.value = isNew
    }
    fun emergencyNewLab(emergency: Emergency) {
        _emergencyNewLab.value = emergency
    }
    fun saveNewLab(emergency: Emergency, lab: AnalisysLab) {
        Emergencys.saveNewLab(emergency.id, lab)
    }
    fun setSpeciality(emergency: Emergency, speciality: String) {
        Emergencys.setSpeciality(emergency.id, speciality)
    }

    fun finish(emergency: Emergency) {
        Emergencys.finishEmergency(emergency)
    }
}