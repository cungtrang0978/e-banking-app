package com.example.e_banking_app.ui.input_otp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.input.RegisterInput
import com.example.e_banking_app.databinding.FragmentInputOtpBinding

class InputOtpFragment : Fragment() {

    private lateinit var inputOtpViewModel: InputOtpViewModel
    private var _binding: FragmentInputOtpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputOtpViewModel = ViewModelProvider(this)[InputOtpViewModel::class.java]
        val otpView = binding.otpView
        val phoneNumber = binding.phoneNumber
        val verifyButton = binding.verify
        val loadingProgressBar = binding.loading
        val resendButton = binding.resend


        phoneNumber.text = arguments?.getString("phoneNumber")

        inputOtpViewModel.inputOtpResult.observe(viewLifecycleOwner,
            Observer { inputOtpResult ->
                inputOtpResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                inputOtpResult.error?.let {
                    onFailure(errorTextId = it)
                }
                inputOtpResult.success?.let {
                    onSuccess(inputOtpResult.success)

                }
            })

        inputOtpViewModel.isValidated.observe(viewLifecycleOwner,
            Observer { isValidated ->
                isValidated ?: return@Observer
                verifyButton.isEnabled = isValidated
            })


        verifyButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            inputOtpViewModel.verifyCode(
                otpView.text.toString(),
                phoneNumber = phoneNumber.text.toString(),
                activity,
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
                inputOtpViewModel.otpChanged(s.toString())
            }
        }

        otpView.addTextChangedListener(afterTextChangedListener)

        fun onInit() {
            loadingProgressBar.visibility = View.VISIBLE
            inputOtpViewModel.sendVerificationCode(
                activity = activity,
                number = phoneNumber.text.toString()
            )
        }
        resendButton.setOnClickListener {
            onInit()
        }

        onInit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onSuccess(inputOtpState: InputOtpState) {

        val appContext = context?.applicationContext ?: return
        if (inputOtpState.codeSent) {
            if (inputOtpState.isVerified) {
                val action =
                    InputOtpFragmentDirections.actionInputOtpFragmentToInputPasswordFragment(
                        RegisterInput(phoneNumber = inputOtpState.phoneNumber)
                    )

                view?.findNavController()
                    ?.navigate(action)
            } else {
                Toast.makeText(
                    appContext,
                    R.string.message_code_sent_already,
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }

    private fun onFailure(@StringRes errorTextId: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(
            appContext,
            errorTextId,
            Toast.LENGTH_LONG
        ).show()
    }


}