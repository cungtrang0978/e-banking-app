package com.example.e_banking_app.ui.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.R
import com.example.e_banking_app.databinding.FragmentLanguageBinding

class LanguageFragment : Fragment() {
    private lateinit var viewModel: LanguageViewModel
    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    private val rdVi get() = binding.rdVi
    private val rdEn get() = binding.rdEn
    private val rdGroup get() = binding.rdGroup


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}