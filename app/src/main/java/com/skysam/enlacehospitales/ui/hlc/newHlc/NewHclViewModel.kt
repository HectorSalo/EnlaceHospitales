package com.skysam.enlacehospitales.ui.hlc.newHlc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skysam.enlacehospitales.dataClasses.emergency.AnalisysLab
import com.skysam.enlacehospitales.dataClasses.emergency.ArticlesMedical
import com.skysam.enlacehospitales.dataClasses.emergency.Doctor
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.dataClasses.emergency.Hospital
import com.skysam.enlacehospitales.dataClasses.emergency.Notification
import com.skysam.enlacehospitales.dataClasses.emergency.Patient
import com.skysam.enlacehospitales.dataClasses.emergency.TransferPatient
import com.skysam.enlacehospitales.dataClasses.emergency.Treatment
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

 private val _speciality = MutableLiveData<String?>().apply { value = null }
 val speciality: LiveData<String?> get() = _speciality

 private val _issueMedical = MutableLiveData<String?>().apply { value = null }
 val issueMedical: LiveData<String?> get() = _issueMedical

 private val _labs = MutableLiveData<MutableList<AnalisysLab>>().apply { value = mutableListOf() }
 val labs: LiveData<MutableList<AnalisysLab>> get() = _labs

 private val _doctors = MutableLiveData<MutableList<Doctor>>().apply { value = mutableListOf() }
 val doctors: LiveData<MutableList<Doctor>> get() = _doctors

 private val _treatment = MutableLiveData<Treatment?>().apply { value = null }
 val treatment: LiveData<Treatment?> get() = _treatment

 private val _strategies = MutableLiveData<String?>().apply { value = null }
 val strategies: LiveData<String?> get() = _strategies

 private val _articles = MutableLiveData<ArticlesMedical?>().apply { value = null }
 val articles: LiveData<ArticlesMedical?> get() = _articles

 private val _secondDoctor = MutableLiveData<Doctor?>().apply { value = null }
 val secondDoctor: LiveData<Doctor?> get() = _secondDoctor

 private val _transfer = MutableLiveData<TransferPatient?>().apply { value = null }
 val transfer: LiveData<TransferPatient?> get() = _transfer

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

 fun setIssue(issueMedical: String) {
  _issueMedical.value = issueMedical
 }

 fun setSpeciality(speciality: String) {
  _speciality.value = speciality
 }

 fun setLabs(lab: AnalisysLab) {
  _labs.value?.add(lab)
  _labs.value = _labs.value
 }

 fun setDoctors(doctor: Doctor) {
  _doctors.value?.add(doctor)
  _doctors.value = _doctors.value
 }

 fun setTreatment(treatment: Treatment?) {
  _treatment.value = treatment
 }

 fun setStrategies(strategy: String) {
  _strategies.value = strategy
 }

 fun setArticles(articlesMedical: ArticlesMedical?) {
  _articles.value = articlesMedical
 }

 fun setSecondDoctor(doctor: Doctor?) {
  _secondDoctor.value = doctor
 }

 fun setTransfer(transferPatient: TransferPatient?) {
  _transfer.value = transferPatient
 }

 fun saveEmergency(emergency: Emergency) {
  Emergencys.saveEmergency(emergency)
 }
}