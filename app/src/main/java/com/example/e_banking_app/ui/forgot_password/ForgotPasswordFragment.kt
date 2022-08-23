package com.example.e_banking_app.ui.forgot_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.e_banking_app.data.data_source.AuthDataSource
import com.example.e_banking_app.data.repository.AuthRepository
import com.example.e_banking_app.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {
    private lateinit var viewModel: ForgotPasswordViewModel
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val phoneNumberEdt get() = binding.phoneNumber
    private val emailEdt get() = binding.mail
    private val identityNumberEdt get() = binding.citizenIdentityCard
    private val sendBtn get() = binding.send
    private val loadingProgressBar get() = binding.loading


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ForgotPasswordViewModel(AuthRepository(AuthDataSource()))

        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phoneNumberEdt.addTextChangedListener(afterTextChangedListener)
        emailEdt.addTextChangedListener(afterTextChangedListener)
        identityNumberEdt.addTextChangedListener(afterTextChangedListener)

        sendBtn.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            sendBtn.isEnabled = false
            viewModel.submit(
                phoneNumber = phoneNumberEdt.text.toString(),
                mail = emailEdt.text.toString(),
                identityNumber = identityNumberEdt.text.toString()
            )
        }

        viewModel.result.observe(
            viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                sendBtn.isEnabled = true
                result.success?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG)
                        .show()
                    view.findNavController().popBackStack()
                }

                result.error?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            })
        viewModel.forgotPasswordFormState.observe(viewLifecycleOwner,
            Observer { state ->
                state ?: return@Observer

                sendBtn.isEnabled = state.isDateValid
                state.phoneNumberError?.let {
                    phoneNumberEdt.error = getString(it)
                }
                state.identityNumber?.let {
                    identityNumberEdt.error = getString(it)
                }
                state.mailError?.let {
                    emailEdt.error = getString(it)
                }
            })


    }

    private val afterTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // ignore
        }

        override fun afterTextChanged(s: Editable) {
            viewModel.dataChanged(
                phoneNumber = phoneNumberEdt.text.toString(),
                identityNumber = identityNumberEdt.text.toString(),
                email = emailEdt.text.toString()
            )
        }
    }

}