package com.skysam.enlacehospitales.ui.hlc.emergencys

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.repositories.Emergencys

class EmergencysViewModel : ViewModel() {
    val emergencys: LiveData<List<Emergency>> = Emergencys.getEmergencys().asLiveData()

    fun delete(emergency: Emergency) {
        Emergencys.deleteEmergency(emergency)
    }
}