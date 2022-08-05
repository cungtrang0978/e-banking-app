package com.example.e_banking_app.ui.input_phone_number

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.e_banking_app.databinding.FragmentInputPhoneNumberBinding
import com.google.firebase.auth.FirebaseAuth


class InputPhoneNumberFragment : Fragment() {
    private lateinit var inputPhoneNumberViewModel: InputPhoneNumberViewModel
    private var _binding: FragmentInputPhoneNumberBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputPhoneNumberViewModel = ViewModelProvider(this)[InputPhoneNumberViewModel::class.java]

        auth = FirebaseAuth.getInstance()
        val phoneNumberEditText = binding.phoneNumber
        val nextButton = binding.next

        inputPhoneNumberViewModel.inputPhoneNumberFormState.observe(viewLifecycleOwner,
            Observer { inputPhoneNumberFormState ->
                if (inputPhoneNumberFormState == null) {
                    return@Observer
                }
                nextButton.isEnabled = inputPhoneNumberFormState.isDateValid
                inputPhoneNumberFormState.phoneNumberError?.let {
                    phoneNumberEditText.error = getString(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                inputPhoneNumberViewModel.dataChanged(
                    phoneNumberEditText.text.toString()
                )
            }
        }

        phoneNumberEditText.addTextChangedListener(afterTextChangedListener)

        nextButton.setOnClickListener {
            val action =
                InputPhoneNumberFragmentDirections.actionInputPhoneNumberFragmentToInputOtpFragment(
                    phoneNumberEditText.text.toString()
                )
            it.findNavController()
                .navigate(action)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}