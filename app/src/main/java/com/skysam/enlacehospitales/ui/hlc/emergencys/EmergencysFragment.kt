package com.skysam.enlacehospitales.ui.hlc.emergencys

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.databinding.FragmentEmergencysBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.details.HospitalDialog
import com.skysam.enlacehospitales.ui.hlc.emergencys.details.IssueMedicalDialog
import com.skysam.enlacehospitales.ui.hlc.emergencys.details.NotificationDialog
import com.skysam.enlacehospitales.ui.hlc.emergencys.details.PatientDialog
import com.skysam.enlacehospitales.ui.hlc.newHlc.NewHlcActivity
import com.skysam.enlacehospitales.ui.main.MainActivity

class EmergencysFragment : Fragment(), MenuProvider, OnClick {

    private var _binding: FragmentEmergencysBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmergencysViewModel by activityViewModels()
    private lateinit var emergencyAdapter: EmergencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmergencysBinding.inflate(inflater, container, false)
        binding.topAppBar.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        binding.topAppBar.setNavigationIcon(R.drawable.ic_back_24)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emergencyAdapter = EmergencyAdapter(this)
        binding.rvEmergencys.apply {
            setHasFixedSize(true)
            adapter = emergencyAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) binding.fab.hide() else binding.fab.show()
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }

        binding.topAppBar.setNavigationOnClickListener { getOut() }

        binding.fab.setOnClickListener { startActivity(Intent(requireContext(), NewHlcActivity::class.java)) }

        susbcribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.top_app_bar_hlc, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
        android.R.id.home -> {
            getOut()
            true
        }
        else -> false
    }

    private fun susbcribeObservers() {
        viewModel.emergencys.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it.isEmpty()) {
                    binding.rvEmergencys.visibility = View.GONE
                    binding.tvListEmpty.visibility = View.VISIBLE
                } else {
                    emergencyAdapter.updateList(it)
                    binding.rvEmergencys.visibility = View.VISIBLE
                    binding.tvListEmpty.visibility = View.GONE
                }
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun getOut() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    override fun view(emergency: Emergency) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.layout_options_emergency)
        bottomSheetDialog.dismissWithAnimation = true
        bottomSheetDialog.show()
        val viewSheet: View? = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        val btnNotification: MaterialCardView = viewSheet!!.findViewById(R.id.card_notification)
        val btnPatient: MaterialCardView = viewSheet.findViewById(R.id.card_patient)
        val btnHospital: MaterialCardView = viewSheet.findViewById(R.id.card_hospital)
        val btnIssue: MaterialCardView = viewSheet.findViewById(R.id.card_issue_medical)

        if (emergency.patient == null) {
            btnPatient.visibility = View.GONE
        }
        btnNotification.setOnClickListener {
            bottomSheetDialog.hide()
            viewModel.viewNotification(emergency.notification)
            val notificationDialog = NotificationDialog()
            notificationDialog.show(requireActivity().supportFragmentManager, tag)
        }
        btnPatient.setOnClickListener {
            bottomSheetDialog.hide()
            viewModel.viewPatient(emergency.patient!!)
            val patientDialog = PatientDialog()
            patientDialog.show(requireActivity().supportFragmentManager, tag)
        }
        btnHospital.setOnClickListener {
            bottomSheetDialog.hide()
            viewModel.viewHospital(emergency.hospital)
            val hospitalDialog = HospitalDialog()
            hospitalDialog.show(requireActivity().supportFragmentManager, tag)
        }
        btnIssue.setOnClickListener {
            bottomSheetDialog.hide()
            viewModel.viewIssueMedical(emergency.issueMedical)
            val issueMedicalDialog = IssueMedicalDialog()
            issueMedicalDialog.show(requireActivity().supportFragmentManager, tag)
        }
    }

    override fun finish(emergency: Emergency) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.title_confirmation_dialog))
            .setMessage(getString(R.string.msg_finish_dialog))
            .setPositiveButton(R.string.text_finish_emergency) { _, _ ->
                viewModel.finish(emergency)
            }
            .setNegativeButton(R.string.text_cancel, null)

        val dialog = builder.create()
        dialog.show()
    }
}