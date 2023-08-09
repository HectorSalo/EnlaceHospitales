package com.skysam.enlacehospitales.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import androidx.preference.SwitchPreferenceCompat
import com.skysam.enlacehospitales.BuildConfig
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.ui.login.LoginActivity
import kotlinx.coroutines.launch

class ProfileFragment : PreferenceFragmentCompat() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var switchBiometric: SwitchPreferenceCompat

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchBiometric = findPreference(getString(R.string.key_fingerprint))!!

        switchBiometric.setOnPreferenceChangeListener { _, newValue ->
            val isOn = newValue as Boolean
            lifecycleScope.launch {
                viewModel.changeBiometric(isOn)
            }
            true
        }

        val btnBack = findPreference<PreferenceScreen>("back")
        btnBack?.setOnPreferenceClickListener {
            findNavController().navigate(R.id.profileFragmentToMainFragment)
            true
        }

        val versionPreferenceScreen = findPreference<PreferenceScreen>("name_version")
        versionPreferenceScreen?.title = getString(R.string.version_name, BuildConfig.VERSION_NAME)

        val signOutPreference: PreferenceScreen = findPreference("signOut")!!
        signOutPreference.setOnPreferenceClickListener {
            signOut()
            true
        }

        subscribeViewModels()
    }

    private fun subscribeViewModels() {
        viewModel.fingerprintActive.observe(viewLifecycleOwner) {
            switchBiometric.isChecked = it
        }
    }

    private fun signOut() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.title_sign_out))
            .setMessage(getString(R.string.message_sign_out))
            .setPositiveButton(R.string.title_sign_out) { _, _ ->
                lifecycleScope.launch {
                    viewModel.signOut()
                    requireActivity().startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
            }
            .setNegativeButton(R.string.text_cancel, null)

        val dialog = builder.create()
        dialog.show()
    }
}