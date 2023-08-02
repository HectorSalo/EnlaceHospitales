package com.skysam.enlacehospitales.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.databinding.ActivityMainBinding
import com.skysam.enlacehospitales.ui.hlc.HlcActivity
import com.skysam.enlacehospitales.ui.login.LoginViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.members.observe(this) {
            if (it.isNotEmpty()) {
                for (member in it) {
                    if (member.guard) {
                        binding.tvName.text = member.name
                        binding.tvPhone.text = member.phone
                        binding.tvCongregation.text = member.congregation
                    }
                }
            }
        }

        val user = EnlaceHospitales.EnlaceHospitales.getCurrentUser()

        val c = Calendar.getInstance()
        binding.tvWelcome.text = when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "${getString(com.firebase.ui.auth.R.string.fui_welcome_back_email_header)}\n" +
                    getString(R.string.welcome_mornning, user.name)
            in 12..18 -> "${getString(com.firebase.ui.auth.R.string.fui_welcome_back_email_header)}\n" +
                    getString(R.string.welcome_afternoon, user.name)
            in 19..23 -> "${getString(com.firebase.ui.auth.R.string.fui_welcome_back_email_header)}\n" +
                    getString(R.string.welcome_night, user.name)
            else -> getString(com.firebase.ui.auth.R.string.fui_welcome_back_email_header)
        }

        binding.cardHlc.setOnClickListener {
            startActivity(Intent(this, HlcActivity::class.java))
            finish()
        }

        binding.cardGvp.setOnClickListener {
            startActivity(Intent(this, HlcActivity::class.java))
            finish()
        }


    }
}