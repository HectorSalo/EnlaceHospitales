package com.skysam.enlacehospitales.ui.hlc.members

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
import com.skysam.enlacehospitales.common.Constants
import com.skysam.enlacehospitales.common.EnlaceHospitales
import com.skysam.enlacehospitales.dataClasses.Member
import com.skysam.enlacehospitales.databinding.FragmentMembersHlcBinding
import com.skysam.enlacehospitales.ui.main.MainActivity

class MembersFragment : Fragment(), MenuProvider, OnClickMember {

    private var _binding: FragmentMembersHlcBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MembersViewModel by activityViewModels()
    private lateinit var memberAdapter: MemberAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMembersHlcBinding.inflate(inflater, container, false)
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memberAdapter = MemberAdapter(this)
        binding.rvMembers.apply {
            setHasFixedSize(true)
            adapter = memberAdapter
        }

        if (EnlaceHospitales.EnlaceHospitales.getCurrentUser().role != Constants.ROLE_ADMIN) binding.fab.hide()

        binding.fab.setOnClickListener {
            val newMemberDialog = NewMemberDialog()
            newMemberDialog.show(requireActivity().supportFragmentManager, tag)
        }

        susbcribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.top_app_bar_hlc, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        android.R.id.home -> {
            getOut()
            true
        }
        else -> false
    }

    private fun susbcribeObservers() {
        viewModel.members.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it.isEmpty()) {
                    binding.rvMembers.visibility = View.GONE
                    binding.tvListEmpty.visibility = View.VISIBLE
                } else {
                    memberAdapter.updateList(it)
                    binding.rvMembers.visibility = View.VISIBLE
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

    override fun view(member: Member) {
        viewModel.viewMember(member)
        val viewDetailsDialog = ViewDetailsDialog()
        viewDetailsDialog.show(requireActivity().supportFragmentManager, tag)
    }

    override fun update(member: Member) {
        viewModel.memberToDialogUpdate(member)
        val updateMemberDialog = UpdateMemberDialog()
        updateMemberDialog.show(requireActivity().supportFragmentManager, tag)
    }

    override fun delete(member: Member) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.title_confirmation_dialog))
            .setMessage(getString(R.string.msg_delete_dialog))
            .setPositiveButton(R.string.text_delete) { _, _ ->
                viewModel.deleteMember(member)
            }
            .setNegativeButton(R.string.text_cancel, null)

        val dialog = builder.create()
        dialog.show()
    }
}