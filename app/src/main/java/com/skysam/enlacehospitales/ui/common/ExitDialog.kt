package com.skysam.enlacehospitales.ui.common

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.skysam.enlacehospitales.R

class ExitDialog(private val onClickExit: OnClickExit): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.title_confirmation_dialog))
            .setMessage(getString(R.string.msg_exit_dialog))
            .setPositiveButton(R.string.text_exit) { _, _ ->
                onClickExit.onClickExit()
            }
            .setNegativeButton(R.string.text_cancel, null)

        val dialog = builder.create()
        dialog.show()

        return dialog
    }
}