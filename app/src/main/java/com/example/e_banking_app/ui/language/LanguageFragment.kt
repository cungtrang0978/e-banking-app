package com.example.e_banking_app.ui.language

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e_banking_app.databinding.FragmentLanguageBinding
import com.example.e_banking_app.utils.LanguageUtils


class LanguageFragment : Fragment() {
    private lateinit var viewModel: LanguageViewModel
    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    private val rdVi get() = binding.rdVi
    private val rdEn get() = binding.rdEn
    private val rdGroup get() = binding.rdGroup
    private val saveBtn get() = binding.saveBtn


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)

        val lang = LanguageUtils.getLang(activity!!)
        if (lang == "vi") {
            rdVi.isChecked = true
        } else {
            rdEn.isChecked = true
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveBtn.setOnClickListener {
            activity?.let { it1 ->
                val lang = if (rdVi.isChecked) "vi" else "en"
                LanguageUtils.changeLang(lang, it1)
                val intent = it1.intent
                it1.overridePendingTransition(0, 0)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                it1.finish()
                it1.overridePendingTransition(0, 0)
                startActivity(intent)
            }

        }

    }

}