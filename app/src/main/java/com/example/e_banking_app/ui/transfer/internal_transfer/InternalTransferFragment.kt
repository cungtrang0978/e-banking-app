package com.example.e_banking_app.ui.transfer.internal_transfer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.data.factory.TransactionViewModelFactory
import com.example.e_banking_app.databinding.FragmentInternalTransferBinding

class InternalTransferFragment : Fragment() {

    private var _binding: FragmentInternalTransferBinding? = null
    private val binding get() = _binding!!

    private val edtRecipientAccountNumber get() = binding.edtRecipientAccountNumber
    private val edtAmount get() = binding.edtAmount
    private val edtMessage get() = binding.edtMessage
    private val txtAccountName get() = binding.txtAccountName
    private val loadingProgressBar get() = binding.loading
    private val confirmBtn get() = binding.confirmButton

    private lateinit var viewModel: InternalTransferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInternalTransferBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            TransactionViewModelFactory(context!!)
        )[InternalTransferViewModel::class.java]

        confirmBtn.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            confirmBtn.isEnabled = false
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtRecipientAccountNumber.addTextChangedListener(afterTextChangedListener)
        edtRecipientAccountNumber.addTextChangedListener(afterAccountTextChangedListener)
        edtAmount.addTextChangedListener(afterTextChangedListener)
        edtMessage.addTextChangedListener(afterTextChangedListener)

        viewModel.internalTransferFormState.observe(viewLifecycleOwner,
            Observer { state ->
                state ?: return@Observer

                state.accountNumberError?.let {
                    edtRecipientAccountNumber.error = getString(it)
                }
                state.amountError?.let {
                    edtAmount.error = getString(it)
                }
                state.messageError?.let {
                    edtMessage.error = getString(it)
                }
            })
        viewModel.internalAccountState.observe(viewLifecycleOwner,
            Observer { state ->
                state ?: return@Observer
                state.name?.let {
                    txtAccountName.text = it
                }
                state.error?.let {
                    txtAccountName.text = ""
                }
            })
    }

    val afterTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // ignore
        }

        override fun afterTextChanged(s: Editable) {
            viewModel.dataChanged(
                edtRecipientAccountNumber.text.toString(),
                edtAmount.text.toString(),
                edtMessage.text.toString(),
            )
        }
    }

    private val afterAccountTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // ignore
        }

        override fun afterTextChanged(s: Editable) {
            viewModel.accountChanged(edtRecipientAccountNumber.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}