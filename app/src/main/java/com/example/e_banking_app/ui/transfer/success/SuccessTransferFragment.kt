package com.example.e_banking_app.ui.transfer.success

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_banking_app.R

class SuccessTransferFragment : Fragment() {

    companion object {
        fun newInstance() = SuccessTransferFragment()
    }

    private lateinit var viewModel: SuccessTransferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_success_transfer, container, false)
    }
}