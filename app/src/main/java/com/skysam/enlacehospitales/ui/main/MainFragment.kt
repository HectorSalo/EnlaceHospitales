package com.skysam.enlacehospitales.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.databinding.FragmentMainBinding
import com.skysam.enlacehospitales.ui.hlc.HlcActivity
import com.skysam.enlacehospitales.ui.login.LoginActivity
import kotlinx.coroutines.launch
import java.util.Calendar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val c = Calendar.getInstance()

        viewModel.members.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                it.forEach {member ->
                    for (day in member.guard) {
                        if (day == c.get(Calendar.DAY_OF_WEEK)) {
                            binding.tvName.text = member.name
                            binding.tvPhone.text = member.phone
                            binding.tvCongregation.text = member.congregation
                        }
                    }
                }
            }
        }

        val user = EnlaceHospitales.EnlaceHospitales.getCurrentUser()


        binding.tvWelcome.text = when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "${getString(com.firebase.ui.auth.R.string.fui_welcome_back_email_header)}\n" +
                    getString(R.string.welcome_mornning, user.name)
            in 12..18 -> "${getString(com.firebase.ui.auth.R.string.fui_welcome_back_email_header)}\n" +
                    getString(R.string.welcome_afternoon, user.name)
            in 19..23 -> "${getString(com.firebase.ui.auth.R.string.fui_welcome_back_email_header)}\n" +
                    getString(R.string.welcome_night, user.name)
            else -> getString(com.firebase.ui.auth.R.string.fui_welcome_back_email_header)
        }

        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${user.phone}")
            startActivity(intent)
        }

        binding.btnCopy.setOnClickListener { copy(user.phone) }

        binding.cardHlc.setOnClickListener {
            startActivity(Intent(requireContext(), HlcActivity::class.java))
            requireActivity().finish()
        }

        binding.cardGvp.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle(getString(R.string.title_soon))
                .setMessage(getString(R.string.message_soon))
                .setIcon(R.drawable.ic_soon_24)
                .setPositiveButton(R.string.text_accept, null)

            val dialog = builder.create()
            dialog.show()
            /*startActivity(Intent(requireContext(), HlcActivity::class.java))
            requireActivity().finish()*/
        }

        binding.btnProfile.setOnClickListener { findNavController().navigate(R.id.mainFragmentToProfileFragment) }
    }

    private fun copy(phone: String) {
        val clipboardManager = requireActivity().getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("text", phone)
        clipboardManager.setPrimaryClip(clip)
        Snackbar.make(binding.root, "Copiado", Snackbar.LENGTH_SHORT).show()
    }
}