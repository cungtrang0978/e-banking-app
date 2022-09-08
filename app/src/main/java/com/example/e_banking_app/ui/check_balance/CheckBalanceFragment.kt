package com.example.e_banking_app.ui.check_balance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.R
import com.example.e_banking_app.data.factory.UserViewModelFactory
import com.example.e_banking_app.databinding.FragmentCheckBalanceBinding
import com.example.e_banking_app.utils.CurrencyUtils

class CheckBalanceFragment : Fragment() {
    private var _binding: FragmentCheckBalanceBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CheckBalanceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(context!!)
        )[CheckBalanceViewModel::class.java]
        _binding = FragmentCheckBalanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.infoResult.observe(viewLifecycleOwner) { result ->
            result ?: return@observe
            result.success?.let {
                binding.accountBalance.text = CurrencyUtils.format(it.money)
            }
            result.error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchProfile()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}