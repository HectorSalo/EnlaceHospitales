package com.skysam.enlacehospitales.ui.hlc.members

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.databinding.DialogNewMemberBinding
import com.skysam.enlacehospitales.ui.common.DatePicker
import com.skysam.enlacehospitales.ui.common.ExitDialog
import com.skysam.enlacehospitales.ui.common.OnClickDateTime
import com.skysam.enlacehospitales.ui.common.OnClickExit
import java.util.Date

class NewMemberDialog: DialogFragment(), OnClickExit, OnClickDateTime {
    private var _binding: DialogNewMemberBinding? = null
    private val binding get() = _binding!!
    private lateinit var dateSelected: Date
    private val viewModel: MembersViewModel by activityViewModels()
    private var members = listOf<Member>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            com.google.android.material.R.style.ShapeAppearanceOverlay_MaterialComponents_MaterialCalendar_Window_Fullscreen
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNewMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val exitDialog = ExitDialog(this@NewMemberDialog)
                exitDialog.show(requireActivity().supportFragmentManager, tag)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        dateSelected = Date()
        binding.etDate.setText(Utils.convertDateToString(dateSelected))

        binding.etName.doAfterTextChanged { binding.tfName.error = null }
        binding.etEmail.doAfterTextChanged { binding.tfEmail.error = null }
        binding.etPassword.doAfterTextChanged { binding.tfPassword.error = null }
        binding.etPasswordRepeat.doAfterTextChanged { binding.tfPasswordRepeat.error = null }
        binding.etPhone.doAfterTextChanged { binding.tfPhone.error = null }

        binding.etDate.setOnClickListener {
            val datePicker = DatePicker(requireActivity(), this)
            datePicker.viewCalendar()
        }

        binding.btnExit.setOnClickListener {
            val exitDialog = ExitDialog(this@NewMemberDialog)
            exitDialog.show(requireActivity().supportFragmentManager, tag)
        }

        binding.btnSave.setOnClickListener { validateData() }

        susbcribeViewModels()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun susbcribeViewModels() {
        viewModel.members.observe(viewLifecycleOwner) {
            members = it
        }
    }

    private fun validateData() {
        Utils.close(binding.root)
        if (binding.etName.text.toString().isEmpty()) {
            binding.tfName.error = getString(R.string.error_field_empty)
            binding.etName.requestFocus()
            return
        }
        if (binding.etEmail.text.toString().isEmpty()) {
            binding.tfEmail.error = getString(R.string.error_field_empty)
            binding.etEmail.requestFocus()
            return
        }
        val email = binding.etEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tfEmail.error = getString(R.string.error_email_invalid)
            binding.etEmail.requestFocus()
            return
        }
        members.forEach {
            if (it.email == email) {
                binding.tfEmail.error = getString(R.string.error_email_repeated)
                binding.etEmail.requestFocus()
                return
            }
        }
        if (binding.etPassword.text.toString().isEmpty()) {
            binding.tfPassword.error = getString(R.string.error_field_empty)
            binding.etPassword.requestFocus()
            return
        }
        val password = binding.etPassword.text.toString()
        if (password.length < 6) {
            binding.tfPassword.error = getString(R.string.error_password_length)
            binding.etPassword.requestFocus()
            return
        }
        val passwordRepeat = binding.etPasswordRepeat.text.toString()
        if (passwordRepeat != password) {
            binding.tfPasswordRepeat.error = getString(R.string.error_password_match)
            binding.etPasswordRepeat.requestFocus()
            return
        }
        val listGuard = mutableListOf<Int>()
        if (binding.cbSunday.isChecked) listGuard.add(1)
        if (binding.cbMonday.isChecked) listGuard.add(2)
        if (binding.cbTuesday.isChecked) listGuard.add(3)
        if (binding.cbWendsday.isChecked) listGuard.add(4)
        if (binding.cbThursday.isChecked) listGuard.add(5)
        if (binding.cbFriday.isChecked) listGuard.add(6)
        if (binding.cbSaturday.isChecked) listGuard.add(7)

        val newMember = Member(
            "",
            binding.etName.text.toString(),
            email,
            password,
            binding.etCongregation.text.toString(),
            binding.etPhone.text.toString(),
            if (binding.rbAdmin.isChecked) Constants.ROLE_ADMIN else Constants.ROLE_HLC,
            dateSelected,
            true,
            listGuard
        )

        viewModel.saveMember(newMember)
        dismiss()
    }

    override fun onClickExit() {
        dismiss()
    }

    override fun dateSelected(date: Date) {
        dateSelected = date
        binding.etDate.setText(Utils.convertDateToString(dateSelected))
    }

    override fun timeSelected(hour: Int, minute: Int) {}
}