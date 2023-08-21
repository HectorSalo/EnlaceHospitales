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
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency
import com.skysam.enlacehospitales.databinding.FragmentEmergencysBinding
import com.skysam.enlacehospitales.ui.main.MainActivity
import com.skysam.enlacehospitales.ui.hlc.newHlc.NewHlcActivity

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
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emergencyAdapter = EmergencyAdapter(this)
        binding.rvEmergencys.apply {
            setHasFixedSize(true)
            adapter = emergencyAdapter
        }

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

    }

    override fun delete(emergency: Emergency) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.title_confirmation_dialog))
            .setMessage(getString(R.string.msg_delete_dialog))
            .setPositiveButton(R.string.text_delete) { _, _ ->
                viewModel.delete(emergency)
            }
            .setNegativeButton(R.string.text_cancel, null)

        val dialog = builder.create()
        dialog.show()
    }
}