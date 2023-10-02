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

    private val _emergencyToView = MutableLiveData<Emergency>()
    val emergencyToView: LiveData<Emergency> get() = _emergencyToView

    private val _newLab = MutableLiveData<Boolean>()
    val newLab: LiveData<Boolean> get() = _newLab

    fun newLab(isNew: Boolean) {
        _newLab.value = isNew
    }
    fun emergencyToView(emergency: Emergency) {
        _emergencyToView.value = emergency
    }
    fun saveNewLab(emergency: Emergency, lab: AnalisysLab) {
        Emergencys.saveNewLab(emergency.id, lab)
    }

    fun saveSpeciality(emergency: Emergency, speciality: String) {
        Emergencys.setSpeciality(emergency.id, speciality)
    }

    fun finish(emergency: Emergency) {
        Emergencys.finishEmergency(emergency)
    }
}