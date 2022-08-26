package com.example.e_banking_app.ui.transfer.transaction_confirmation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_banking_app.R

class TransactionConfirmationFragment : Fragment() {

    companion object {
        fun newInstance() = TransactionConfirmationFragment()
    }

    private lateinit var viewModel: TransactionConfirmationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transaction_confirmation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionConfirmationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}