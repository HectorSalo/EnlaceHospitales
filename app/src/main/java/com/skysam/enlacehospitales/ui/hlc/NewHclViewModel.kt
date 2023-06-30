package com.skysam.enlacehospitales.ui.hlc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Hector Chirinos on 19/06/2023.
 */

class NewHclViewModel: ViewModel() {
 private val _step = MutableLiveData<Int>().apply { value = 0 }
 val step: LiveData<Int> get() = _step

 fun goStep(step: Int) {
  _step.value = step
 }
}