package com.example.e_banking_app.ui.input_password

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
import androidx.navigation.fragment.navArgs
import com.example.e_banking_app.databinding.FragmentInputPasswordBinding

class InputPasswordFragment : Fragment() {
    private lateinit var viewModel: InputPasswordViewModel
    private var _binding: FragmentInputPasswordBinding? = null
    private val binding get() = _binding!!

    val args: InputPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[InputPasswordViewModel::class.java]

        val passwordEditText = binding.password
        val confirmPasswordEditText = binding.confirmPassword
        val nextButton = binding.next


        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.dataChanged(
                    passwordEditText.text.toString(),
                    confirmPasswordEditText.text.toString(),
                )
            }
        }
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener)

        viewModel.inputPasswordFormState.observe(viewLifecycleOwner,
            Observer { inputPasswordFormState ->
                inputPasswordFormState ?: return@Observer
                nextButton.isEnabled = inputPasswordFormState.isValid
                inputPasswordFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                    confirmPasswordEditText.error = getString(it)
                }
            })

        nextButton.setOnClickListener {
            val registerInput = args.registerInput.copy(
                password = passwordEditText.text.toString(),
                confirmPassword = confirmPasswordEditText.text.toString()
            )
            val action =
                InputPasswordFragmentDirections.actionInputPasswordFragmentToRegisterFragment(
                    registerInput
                )

            view.findNavController().navigate(action)

        }
    }

}