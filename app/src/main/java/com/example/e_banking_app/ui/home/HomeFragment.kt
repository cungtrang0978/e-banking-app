package com.example.e_banking_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.e_banking_app.R
import com.example.e_banking_app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val passbookBtn get() = binding.passbook
    private val transferBtn get() = binding.transfer
    private val billBtn get() = binding.bill


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        R.string.airline_booking
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passbookBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToPassbookFragment()
            findNavController().navigate(action)
        }
        transferBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToTransferSelectionFragment()
            findNavController().navigate(action)
        }

        billBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToBillListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}