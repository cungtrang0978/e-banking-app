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
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.example.e_banking_app.data.factory.TransactionViewModelFactory
import com.example.e_banking_app.data.factory.UserViewModelFactory
import com.example.e_banking_app.data.model.bank.Bank
import com.example.e_banking_app.databinding.FragmentInterbankTransferBinding
import com.example.e_banking_app.ui.input_otp.InputOtpState
import com.example.e_banking_app.ui.otp.OtpVerificationViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class InterbankTransferFragment : Fragment() {
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
            showBottomSheetDialog()
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

                confirmBtn.isEnabled = state.isValid
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

                selectedBank = if (list.isNotEmpty()) list.first() else null
                context?.let {
                    val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                        it,
                        R.layout.simple_spinner_item,
                        list.map { bank -> bank.name_bank }
                    )
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    beneficiaryBankSpinner.adapter = spinnerArrayAdapter
                    beneficiaryBankSpinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                p0: AdapterView<*>?,
                                p1: View?,
                                pos: Int,
                                p3: Long
                            ) {
                                viewModel.bankList.value?.let { list ->
                                    selectedBank = list[pos]
                                    checkAccount()
                                }
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                }

            })

        viewModel.interbankTransferResult.observe(
            viewLifecycleOwner
        ) { state ->
            state ?: return@observe

            loadingProgressBar.visibility = View.GONE
            confirmBtn.isEnabled = true
            state.success?.let {
                Toast.makeText(
                    context,
                    it,
                    Toast.LENGTH_LONG
                ).show()

                val action =
                    InterbankTransferFragmentDirections.actionInterbankTransferFragmentToSuccessTransferFragment()
                findNavController().navigate(action)
            }
            state.error?.let {
                Toast.makeText(
                    context,
                    it,
                    Toast.LENGTH_LONG
                ).show()

            }
        }
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

    private fun showBottomSheetDialog() {

        context?.let { context ->
            val otpVerificationViewModel =
                ViewModelProvider(
                    this,
                    UserViewModelFactory(context)
                )[OtpVerificationViewModel::class.java]

            otpVerificationViewModel.sendVerificationCode(activity)
            val dialog = BottomSheetDialog(context)

            dialog.setContentView(com.example.e_banking_app.R.layout.fragment_otp_bottom_sheet)
//            val _binding: FragmentOtpBottomSheetBinding =
//                FragmentOtpBottomSheetBinding.inflate(layoutInflater)

            val otpView =
                dialog.findViewById<PinEntryEditText>(com.example.e_banking_app.R.id.otpView)
            val verifyBtn = dialog.findViewById<Button>(com.example.e_banking_app.R.id.verify)

            fun onSuccess(inputOtpState: InputOtpState) {

                val appContext = context.applicationContext ?: return
                if (inputOtpState.codeSent) {
                    if (inputOtpState.isVerified) {
                        dialog.dismiss()

                        onConfirm()

                    } else {
                        Toast.makeText(
                            appContext,
                            com.example.e_banking_app.R.string.message_code_sent_already,
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }
            }

            otpVerificationViewModel.inputOtpResult.observe(viewLifecycleOwner,
                Observer { inputOtpResult ->
                    inputOtpResult ?: return@Observer
//                    loadingProgressBar.visibility = View.GONE
                    inputOtpResult.error?.let {
//                        onFailure(errorTextId = it)
                        Toast.makeText(context, it, Toast.LENGTH_LONG)
                            .show()
                        dialog.dismiss()
                    }
                    inputOtpResult.success?.let { state ->
                        onSuccess(state)
                    }
                })


            val afterTextChangedListener = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // ignore
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    // ignore
                }

                override fun afterTextChanged(s: Editable) {
                    otpVerificationViewModel.otpChanged(s.toString())
                }
            }

            otpView?.addTextChangedListener(afterTextChangedListener)

            otpVerificationViewModel.isValidated.observe(viewLifecycleOwner,
                Observer { isValidated ->
                    isValidated ?: return@Observer
                    verifyBtn?.isEnabled = isValidated
                })

            verifyBtn?.setOnClickListener {
                otpVerificationViewModel.verifyCode(
                    otpView?.text.toString(),
                    activity,
                )
            }

            fun onInit() {
//                loadingProgressBar.visibility = View.VISIBLE
                otpVerificationViewModel.sendVerificationCode(
                    activity = activity
                )
            }

            onInit()

            dialog.show()
        }


    }


    private fun onConfirm() {
        selectedBank?.let { selectedBank ->
            loadingProgressBar.visibility = View.VISIBLE
            confirmBtn.isEnabled = false
            viewModel.confirm(
                edtRecipientAccountNumber.text.toString(),
                edtAmount.text.toString(),
                edtMessage.text.toString(),
                selectedBank
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}