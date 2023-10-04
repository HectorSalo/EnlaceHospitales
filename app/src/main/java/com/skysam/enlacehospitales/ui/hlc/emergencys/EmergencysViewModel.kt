package com.skysam.enlacehospitales.ui.hlc.emergencys

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab
import com.skysam.enlacehospitales.dataClasses.emergency.Doctor
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.repositories.Emergencys

class EmergencysViewModel : ViewModel() {
    val emergencys: LiveData<List<Emergency>> = Emergencys.getEmergencys().asLiveData()

    private val _emergencyToView = MutableLiveData<Emergency>()
    val emergencyToView: LiveData<Emergency> get() = _emergencyToView

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
    fun saveNewLab(emergency: Emergency, lab: AnalisysLab) {
        Emergencys.saveNewLab(emergency.id, lab)
    }

    fun saveNewDoctor(emergency: Emergency, doctor: Doctor) {
        Emergencys.saveNewDoctor(emergency.id, doctor)
    }

    fun finish(emergency: Emergency) {
        Emergencys.finishEmergency(emergency)
    }
}