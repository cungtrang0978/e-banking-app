package com.example.e_banking_app.ui.transfer.interbank_transfer

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.data.factory.TransactionViewModelFactory
import com.example.e_banking_app.data.model.bank.Bank
import com.example.e_banking_app.databinding.FragmentInterbankTransferBinding

class InterbankTransferFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentInterbankTransferBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: InterbankTransferViewModel

    private val edtRecipientAccountNumber get() = binding.edtRecipientAccountNumber
    private val edtAmount get() = binding.edtAmount
    private val edtMessage get() = binding.edtMessage
    private val txtAccountName get() = binding.txtAccountName
    private val loadingProgressBar get() = binding.loading
    private val beneficiaryBankSpinner get() = binding.beneficiaryBankSpinner
    private val confirmBtn get() = binding.confirmButton


    private var selectedBank: Bank? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInterbankTransferBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            TransactionViewModelFactory(context!!)
        )[InterbankTransferViewModel::class.java]

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

        viewModel.interbankTransferFormState.observe(viewLifecycleOwner,
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
        viewModel.interbankAccountState.observe(viewLifecycleOwner,
            Observer { state ->
                state ?: return@Observer
                state.name?.let {
                    txtAccountName.text = it
                }
                state.error?.let {
                    txtAccountName.text = ""
                }
            })

        viewModel.bankList.observe(viewLifecycleOwner,
            Observer { list ->
                list ?: return@Observer

                context?.let {
                    val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        it,
                        R.layout.simple_spinner_item,
                        list.map { bank -> bank.name_bank }
                    )
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    beneficiaryBankSpinner.adapter = spinnerArrayAdapter
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
            checkAccount()
        }
    }

    private fun checkAccount() {
        selectedBank ?: return

        viewModel.accountChanged(edtRecipientAccountNumber.text.toString(), selectedBank!!)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        viewModel.bankList.value?.let { list ->
            selectedBank = list[pos]
            checkAccount()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}