package com.example.e_banking_app.ui.transfer.internal_transfer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.example.e_banking_app.R
import com.example.e_banking_app.data.factory.TransactionViewModelFactory
import com.example.e_banking_app.data.factory.UserViewModelFactory
import com.example.e_banking_app.databinding.FragmentInternalTransferBinding
import com.example.e_banking_app.ui.input_otp.InputOtpState
import com.example.e_banking_app.ui.otp.OtpVerificationViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class InternalTransferFragment : Fragment() {

    private var _binding: FragmentInternalTransferBinding? = null
    private val binding get() = _binding!!

    private val edtRecipientAccountNumber get() = binding.edtRecipientAccountNumber
    private val edtAmount get() = binding.edtAmount
    private val edtMessage get() = binding.edtMessage
    private val txtAccountName get() = binding.txtAccountName
    private val loadingProgressBar get() = binding.loading
    private val confirmBtn get() = binding.confirmButton

    private lateinit var internalTransferViewModel: InternalTransferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInternalTransferBinding.inflate(inflater, container, false)
        internalTransferViewModel = ViewModelProvider(
            this,
            TransactionViewModelFactory(context!!)
        )[InternalTransferViewModel::class.java]

        confirmBtn.setOnClickListener {
//            loadingProgressBar.visibility = View.VISIBLE
//            confirmBtn.isEnabled = false
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

        internalTransferViewModel.internalTransferFormState.observe(viewLifecycleOwner,
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

        internalTransferViewModel.internalAccountState.observe(viewLifecycleOwner,
            Observer { state ->
                state ?: return@Observer
                state.name?.let {
                    txtAccountName.text = it
                }
                state.error?.let {
                    txtAccountName.text = ""
                }
            })

        internalTransferViewModel.internalTransferResult.observe(
            viewLifecycleOwner,
            Observer { state ->
                state ?: return@Observer

                loadingProgressBar.visibility = View.GONE
                confirmBtn.isEnabled = true
                state.success?.let {
                    Toast.makeText(
                        context,
                        it,
                        Toast.LENGTH_LONG
                    ).show()

                    val action =
                        InternalTransferFragmentDirections.actionInternalTransferFragmentToSuccessTransferFragment()
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
        )
    }

    val afterTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // ignore
        }

        override fun afterTextChanged(s: Editable) {
            internalTransferViewModel.dataChanged(
                edtRecipientAccountNumber.text.toString(),
                edtAmount.text.toString(),
                edtMessage.text.toString(),
            )
        }
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

            dialog.setContentView(R.layout.fragment_otp_bottom_sheet)
//            val _binding: FragmentOtpBottomSheetBinding =
//                FragmentOtpBottomSheetBinding.inflate(layoutInflater)

            val otpView = dialog.findViewById<PinEntryEditText>(R.id.otpView)
            val verifyBtn = dialog.findViewById<Button>(R.id.verify)

            fun onSuccess(inputOtpState: InputOtpState) {

                val appContext = context.applicationContext ?: return
                if (inputOtpState.codeSent) {
                    if (inputOtpState.isVerified) {
                        dialog.dismiss()

                        onConfirm()

                    } else {
                        Toast.makeText(
                            appContext,
                            R.string.message_code_sent_already,
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
        loadingProgressBar.visibility = View.VISIBLE
        confirmBtn.isEnabled = false
        internalTransferViewModel.confirm(
            edtRecipientAccountNumber.text.toString(),
            edtAmount.text.toString(),
            edtMessage.text.toString(),
        )
    }

    private val afterAccountTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // ignore
        }

        override fun afterTextChanged(s: Editable) {
            internalTransferViewModel.accountChanged(edtRecipientAccountNumber.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}