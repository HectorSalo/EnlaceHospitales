package com.skysam.enlacehospitales.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.skysam.enlacehospitales.dataClasses.User
import com.skysam.enlacehospitales.repositories.Auth
import com.skysam.enlacehospitales.repositories.UsersRepository

class LoginViewModel: ViewModel() {
    val users: LiveData<List<User>> = UsersRepository.getUsers().asLiveData()

    fun initSession(email: String, password: String): LiveData<String?> {
        return Auth.initSession(email, password).asLiveData()
    }
}