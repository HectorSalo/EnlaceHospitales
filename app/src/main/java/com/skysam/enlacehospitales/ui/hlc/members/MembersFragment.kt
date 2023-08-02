package com.skysam.enlacehospitales.ui.hlc.members

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentMembersHlcBinding
import com.skysam.enlacehospitales.ui.main.MainActivity
import com.skysam.enlacehospitales.ui.hlc.newHlc.NewHlcActivity

class MembersFragment : Fragment(), MenuProvider {

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

        memberAdapter = MemberAdapter()
        binding.rvMembers.apply {
            setHasFixedSize(true)
            adapter = memberAdapter
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
                    memberAdapter.updateList(it)
                    binding.rvMembers.visibility = View.GONE
                    binding.tvListEmpty.visibility = View.VISIBLE
                } else {
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
}