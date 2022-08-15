package com.example.e_banking_app.ui.passbook

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_banking_app.R

class PassbookFragment : Fragment() {

    companion object {
        fun newInstance() = PassbookFragment()
    }

    private lateinit var viewModel: PassbookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_passbook, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PassbookViewModel::class.java)
        // TODO: Use the ViewModel
    }

}