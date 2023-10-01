package com.skysam.enlacehospitales.ui.hlc.newHlc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.common.Utils
import com.skysam.enlacehospitales.dataClasses.emergency.ArticlesMedical
import com.skysam.enlacehospitales.databinding.FragmentArticlesBinding

class ArticlesFragment : Fragment() {

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewHclViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            viewModel.goStep(7)
            findNavController().navigate(R.id.action_articlesFragment_to_strategiesFragment)
        }

        binding.btnNext.setOnClickListener { saveData() }

        subscribeObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        viewModel.articles.observe(viewLifecycleOwner) {
            if (_binding != null) {
                if (it != null) {
                    binding.etArticles.setText(it.articles)
                    binding.cbDoctorColaborated.isChecked = it.isDoctorColaborated
                }
            }
        }
    }

    private fun saveData() {
        Utils.close(binding.root)
        val articlesMedical = ArticlesMedical(
            binding.etArticles.text.toString().ifEmpty { "" },
            binding.cbDoctorColaborated.isChecked
        )
        viewModel.setArticles(articlesMedical)
        viewModel.goStep(9)
        findNavController().navigate(R.id.action_articlesFragment_to_consultFragment)
    }
}