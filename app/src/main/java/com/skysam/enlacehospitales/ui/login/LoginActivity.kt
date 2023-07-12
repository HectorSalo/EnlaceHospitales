package com.skysam.enlacehospitales.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.widget.doAfterTextChanged
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.User
import com.skysam.enlacehospitales.databinding.ActivityLoginBinding
import com.skysam.enlacehospitales.databinding.ActivityMainBinding
import com.skysam.enlacehospitales.repositories.Auth
import com.skysam.enlacehospitales.ui.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var users = listOf<User>()

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.users.observe(this) {
            if (it.isNotEmpty()) users = it
        }

        binding.etUser.doAfterTextChanged { binding.tfUser.error = null }
        binding.etPassword.doAfterTextChanged { binding.tfPassword.error = null }

        binding.buttonLogin.setOnClickListener { validateUser() }
    }

    override fun onStart() {
        super.onStart()
        if (Auth.getCurrentUser() != null) {
            goActivity()
        }
    }

    private fun validateUser() {
        binding.tfUser.error = null
        binding.tfPassword.error = null

        val email = binding.etUser.text.toString().trim()
        if (email.isEmpty()) {
            binding.tfUser.error = getString(R.string.error_field_empty)
            binding.etUser.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tfUser.error = getString(R.string.error_email_invalid)
            binding.etUser.requestFocus()
            return
        }
        val password = binding.etPassword.text.toString().trim()
        if (password.isEmpty()) {
            binding.tfPassword.error = getString(R.string.error_field_empty)
            binding.etPassword.requestFocus()
            return
        }
        binding.progressBar.visibility = View.VISIBLE
        binding.buttonLogin.isEnabled = false
        binding.tfUser.isEnabled = false
        binding.tfPassword.isEnabled = false
        Utils.close(binding.root)

        var isValid = false
        for (user in users) {
            if (email == user.email && password == user.password) {
               isValid = true
                break
            }
        }

        if (isValid) {
            goActivity()
        }
        //viewModel.initSession(email, password)
    }

    private fun goActivity() {
        val test = User(
            "",
            "Hector Chirinos",
            "a@b.com",
            "1234",
            "El Paraiso",
            "0000-000.00.00",
            "admin"
        )
        EnlaceHospitales.EnlaceHospitales.setCurrentUser(test)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}