package com.skysam.enlacehospitales.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val user = EnlaceHospitales.EnlaceHospitales.getCurrentUser()

        val c = Calendar.getInstance()
        binding.tvWelcome.text = when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> getString(R.string.welcome_mornning, user.name)
            in 12..18 -> getString(R.string.welcome_afternoon, user.name)
            in 19..23 -> getString(R.string.welcome_night, user.name)
            else -> "Bienvenido"
        }*/
    }
}