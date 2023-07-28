package com.skysam.enlacehospitales.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.dataClasses.User
import com.skysam.enlacehospitales.repositories.Users

class LoginViewModel: ViewModel() {
    val users: LiveData<List<User>> = Users.getUsers().asLiveData()
}