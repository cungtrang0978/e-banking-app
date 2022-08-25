package com.example.e_banking_app.ui.transfer.transfer_selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_banking_app.databinding.FragmentTransferSelectionBinding

class TransferSelectionFragment : Fragment() {

    private var _binding: FragmentTransferSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferSelectionBinding.inflate(inflater, container, false)
        binding.interbankTransfer.setOnClickListener {
            val action =
                TransferSelectionFragmentDirections.actionTransferSelectionFragmentToInterbankTransferFragment()
            findNavController().navigate(action)
        }
        binding.internalTransfer.setOnClickListener {
            val action =
                TransferSelectionFragmentDirections.actionTransferSelectionFragmentToInternalTransferFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }
}