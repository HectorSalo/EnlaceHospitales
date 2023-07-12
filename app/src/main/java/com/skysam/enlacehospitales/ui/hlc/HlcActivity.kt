package com.skysam.enlacehospitales.ui.hlc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.ActivityHlcBinding
import com.skysam.enlacehospitales.databinding.ActivityMainBinding

class HlcActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHlcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHlcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_hlc) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
    }
}