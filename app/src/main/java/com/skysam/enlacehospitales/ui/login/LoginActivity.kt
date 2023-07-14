package com.skysam.enlacehospitales.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.User
import com.skysam.enlacehospitales.databinding.ActivityLoginBinding
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

        binding.buttonLogin.setOnClickListener { validateFields() }


        startActivity(Intent(this, MainActivity::class.java))
        finish()
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
        var currentUser: User? = null
        for (user in users) {
            if (user.email == email) {
                exists = true
                currentUser = user
                break
            }
        }
        if (!exists) {
            Snackbar.make(binding.root, getString(R.string.error_user_not_found), Snackbar.LENGTH_SHORT).show()
            showComponents(true)
            return
        }

        if (currentUser?.password != password) {
            Snackbar.make(binding.root, getString(R.string.error_password_incorrect), Snackbar.LENGTH_SHORT).show()
            showComponents(true)
            return
        }

        goActivity(currentUser)
    }

    private fun goActivity(user: User) {
        EnlaceHospitales.EnlaceHospitales.setCurrentUser(user)
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