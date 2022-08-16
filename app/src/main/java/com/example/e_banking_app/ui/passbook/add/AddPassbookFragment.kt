package com.example.e_banking_app.ui.passbook.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.R
import com.example.e_banking_app.data.repository.PassbookRepository
import com.example.e_banking_app.databinding.FragmentAddPassbookBinding

class AddPassbookFragment : Fragment() {

    private lateinit var viewModel: AddPassbookViewModel

    private var _binding: FragmentAddPassbookBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPassbookBinding.inflate(inflater, container, false)

        viewModel = AddPassbookViewModel(passbookRepository = PassbookRepository(context!!))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}