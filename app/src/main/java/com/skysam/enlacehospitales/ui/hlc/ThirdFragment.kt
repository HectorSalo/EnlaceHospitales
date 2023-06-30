package com.skysam.enlacehospitales.ui.hlc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.databinding.FragmentSecondNewHlcBinding
import com.skysam.enlacehospitales.databinding.FragmentThirdNewHclBinding

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdNewHclBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdNewHclBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}