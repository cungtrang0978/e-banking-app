package com.example.e_banking_app.ui.transfer.success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_banking_app.databinding.FragmentSuccessTransferBinding
import com.example.e_banking_app.utils.CurrencyUtils

class SuccessTransferFragment : Fragment() {

    private val args: SuccessTransferFragmentArgs by navArgs()

    private var _binding: FragmentSuccessTransferBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        val callback = object : OnBackPressedCallback(
//            true
//            /** true means that the callback is enabled */
//        ) {
//            override fun handleOnBackPressed() {
//                Toast.makeText(context, "test back", Toast.LENGTH_LONG).show()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessTransferBinding.inflate(inflater, container, false)
        binding.amount.text = CurrencyUtils.format(args.transaction.money)
        binding.receiverName.text = args.transaction.to_name_customer
        binding.transactionCode.text = args.transaction.id_tranfer
        binding.createdAt.text = args.transaction.time_tranfer
        binding.transferMessage.text = args.transaction.message
        binding.beneficiaryBank.text = args.transaction.to_bank_name
        binding.backToHomeBtn.setOnClickListener {
            val action =
                SuccessTransferFragmentDirections.actionSuccessTransferFragmentToNavigationHome()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}