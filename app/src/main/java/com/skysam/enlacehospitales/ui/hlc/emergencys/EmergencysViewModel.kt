package com.skysam.enlacehospitales.ui.hlc.emergencys

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab
import com.skysam.enlacehospitales.dataClasses.emergency.ArticlesMedical
import com.skysam.enlacehospitales.dataClasses.emergency.Doctor
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.dataClasses.emergency.Tracing
import com.skysam.enlacehospitales.dataClasses.emergency.TransferPatient
import com.skysam.enlacehospitales.dataClasses.emergency.Treatment
import com.skysam.enlacehospitales.repositories.Emergencys

class EmergencysViewModel : ViewModel() {
    val emergencys: LiveData<List<Emergency>> = Emergencys.getEmergencys().asLiveData()

    private val _emergencyToView = MutableLiveData<Emergency>()
    val emergencyToView: LiveData<Emergency> get() = _emergencyToView

    private val _isView = MutableLiveData<Boolean>()
    val isView: LiveData<Boolean> get() = _isView

    private val _newLab = MutableLiveData<Boolean>()
    val newLab: LiveData<Boolean> get() = _newLab

    private val _newDoctor = MutableLiveData<Boolean>()
    val newDoctor: LiveData<Boolean> get() = _newDoctor

    fun newLab(isNew: Boolean) {
        _newLab.value = isNew
    }

    fun newDoctor(isNew: Boolean) {
        _newDoctor.value = isNew
    }
    fun emergencyToView(emergency: Emergency) {
        _emergencyToView.value = emergency
    }

    fun setView(isView: Boolean) {
        _isView.value = isView
    }
    fun saveNewLab(emergency: Emergency, lab: AnalisysLab) {
        Emergencys.saveNewLab(emergency.id, lab)
    }

    fun saveNewDoctor(emergency: Emergency, doctor: Doctor) {
        Emergencys.saveNewDoctor(emergency.id, doctor)
    }

    fun updateNotification(emergency: Emergency, notification: Notification) {
        Emergencys.updateNotification(emergency.id, notification)
    }

    fun updatePatient(emergency: Emergency, patient: Patient) {
        Emergencys.updatePatient(emergency.id, patient)
    }

    fun updateHospital(emergency: Emergency, hospital: Hospital) {
        Emergencys.updateHospital(emergency.id, hospital)
    }

    fun updateIssue(emergency: Emergency, issue: String, speciality: String) {
        Emergencys.updateIssue(emergency.id, issue, speciality)
    }

    fun updateTreatment(emergency: Emergency, treatment: Treatment) {
        Emergencys.updateTreatment(emergency.id, treatment)
    }

    fun updateStrategy(emergency: Emergency, strategy: String) {
        Emergencys.updateStrategy(emergency.id, strategy)
    }

    fun updateArticles(emergency: Emergency, articlesMedical: ArticlesMedical) {
        Emergencys.updateArticles(emergency.id, articlesMedical)
    }

    fun updateConsult(emergency: Emergency, doctor: Doctor) {
        Emergencys.updateConsult(emergency.id, doctor)
    }

    fun updateTransfer(emergency: Emergency, transferPatient: TransferPatient) {
        Emergencys.updateTransfer(emergency.id, transferPatient)
    }

    fun updateResults(emergency: Emergency, tracing: Tracing) {
        Emergencys.updateResult(emergency.id, tracing)
    }

    fun finish(emergency: Emergency) {
        Emergencys.finishEmergency(emergency)
    }
}