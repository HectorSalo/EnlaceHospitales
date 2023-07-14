package com.skysam.enlacehospitales.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.ActivityMainBinding
import com.skysam.enlacehospitales.repositories.Auth
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val c = Calendar.getInstance()
        binding.tvWelcome.text = when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> getString(R.string.welcome_mornning, Auth.getCurrentUser()!!.displayName)
            in 12..18 -> getString(R.string.welcome_afternoon, Auth.getCurrentUser()!!.displayName)
            in 19..23 -> getString(R.string.welcome_night, Auth.getCurrentUser()!!.displayName)
            else -> "Bienvenido"
        }
    }
}