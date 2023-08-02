package com.skysam.enlacehospitales.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.BiometricAvailable
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.databinding.ActivityLoginBinding
import com.skysam.enlacehospitales.ui.main.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var members = listOf<Member>()
    private val viewModel: LoginViewModel by viewModels()
    private var isBiometricAvailable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isBiometricAvailable = BiometricAvailable.biometricIsAvailable()

        initViews()

        binding.buttonLogin.setOnClickListener { validateFields() }
    }

    private fun initViews() {
        var email = Constants.EMAIL
        var password = Constants.PASSWORD
        var isBiometric = false

        viewModel.members.observe(this) {
            if (it.isNotEmpty()) members = it
        }
        viewModel.emailLast.observe(this) {
            email = it
            if (it.isNotEmpty()) binding.etUser.setText(email)
        }
        viewModel.passwordLast.observe(this) {
            password = it
        }
        viewModel.biometricEnable.observe(this) {
            if (isBiometricAvailable) {
                isBiometric = it
                binding.cbBiometric.isChecked = isBiometric
            } else {
                binding.btnBiometric.visibility = View.GONE
                binding.cbBiometric.visibility = View.GONE
            }
        }

        binding.etUser.doAfterTextChanged {
            binding.tfUser.error = null

            if (isBiometricAvailable) {
                if (email != binding.etUser.text.toString()) {
                    binding.cbBiometric.visibility = View.VISIBLE
                    binding.btnBiometric.visibility = View.GONE
                } else {
                    binding.cbBiometric.visibility = View.GONE
                    if (isBiometric) binding.btnBiometric.visibility = View.VISIBLE
                    else binding.btnBiometric.visibility = View.GONE
                }
            }
        }
        binding.etPassword.doAfterTextChanged { binding.tfPassword.error = null }
    }

    private fun validateFields() {
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
        showComponents(false)
        Utils.close(binding.root)

        validateUser(email, password)
    }

    private fun validateUser(email: String, password: String) {
        var exists = false
        var currentMember: Member? = null
        for (user in members) {
            if (user.email == email) {
                exists = true
                currentMember = user
                break
            }
        }
        if (!exists) {
            Snackbar.make(binding.root, getString(R.string.error_user_not_found), Snackbar.LENGTH_SHORT).show()
            showComponents(true)
            return
        }

        if (currentMember?.password != password) {
            Snackbar.make(binding.root, getString(R.string.error_password_incorrect), Snackbar.LENGTH_SHORT).show()
            showComponents(true)
            return
        }

        lifecycleScope.launch {
            viewModel.saveLastSession(email, password)
            viewModel.activeBiometric(binding.cbBiometric.isChecked)
        }
        goActivity(currentMember)
    }

    private fun goActivity(member: Member) {
        EnlaceHospitales.EnlaceHospitales.setCurrentUser(member)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showComponents(status: Boolean) {
        if (status) {
            binding.progressBar.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.VISIBLE
        }
        binding.buttonLogin.isEnabled = status
        binding.tfUser.isEnabled = status
        binding.tfPassword.isEnabled = status
    }
}