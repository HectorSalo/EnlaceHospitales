package com.skysam.enlacehospitales.ui.hlc.emergencys.details

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.DialogLabDetailsBinding
import com.skysam.enlacehospitales.ui.hlc.emergencys.EmergencysViewModel
import com.skysam.enlacehospitales.ui.hlc.newHlc.LabAdapter

class LabDialog: DialogFragment() {
    private var _binding: DialogLabDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmergencysViewModel by activityViewModels()
    private lateinit var labAdapter: LabAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogLabDetailsBinding.inflate(layoutInflater)

        labAdapter = LabAdapter()
        binding.rvLab.apply {
            setHasFixedSize(true)
            adapter = labAdapter
        }

        subscribeViewModel()

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.text_details))
            .setView(binding.root)
            .setPositiveButton(R.string.text_accept, null)
            .setNegativeButton(R.string.text_new_lab) { _, _ ->
                viewModel.newLab(true)
                dismiss()
            }

        val dialog = builder.create()
        dialog.show()

        return dialog
    }

    private fun subscribeViewModel() {
        viewModel.emergencyToView.observe(this.requireActivity()) {
            if (_binding != null) {
                if (it.analisysLab.isNotEmpty()) {
                    labAdapter.updateList(it.analisysLab)
                    binding.rvLab.visibility = View.VISIBLE
                    binding.tvListEmpty.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}