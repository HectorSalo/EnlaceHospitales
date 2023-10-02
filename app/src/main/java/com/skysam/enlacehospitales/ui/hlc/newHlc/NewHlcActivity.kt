package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.ActivityNewHlcBinding

class NewHlcActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewHlcBinding
    private val viewModel: NewHclViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityNewHlcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_new_hlc) as NavHostFragment
        navHostFragment.navController

        val sections = resources.getStringArray(R.array.steps).asList()

        viewModel.step.observe(this) {
            binding.stepView.go(it, true)
            if (it < binding.stepView.currentStep) binding.stepView.done(false)
            if (it < 12) binding.tvSection.text = sections[it]
            if (it == 12) binding.stepView.done(true)
        }
    }
}