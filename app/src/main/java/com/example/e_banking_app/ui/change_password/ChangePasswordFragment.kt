package com.example.e_banking_app.ui.change_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.e_banking_app.data.factory.UserViewModelFactory
import com.example.e_banking_app.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ChangePasswordViewModel

    private val passwordEditText get() = binding.password
    private val newPasswordEditText get() = binding.newPassword
    val confirmPasswordEditText get() = binding.confirmPassword
    private val updateButton get() = binding.update
    private val loading get() = binding.loading

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(context!!)
        )[ChangePasswordViewModel::class.java]
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.dataChanged(
                    newPasswordEditText.text.toString(),
                    confirmPasswordEditText.text.toString(),
                )
            }
        }
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        newPasswordEditText.addTextChangedListener(afterTextChangedListener)
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener)


        viewModel.changePasswordFormState.observe(viewLifecycleOwner,
            Observer { changePasswordState ->
                changePasswordState ?: return@Observer
                updateButton.isEnabled = changePasswordState.isValid
                changePasswordState.confirmPasswordError?.let {
                    confirmPasswordEditText.error = getString(it)
                }
            })

        viewModel.changePasswordResult.observe(viewLifecycleOwner,
            Observer { changePasswordResult ->
                changePasswordResult ?: return@Observer
                loading.visibility = View.GONE
                updateButton.isEnabled = true
                changePasswordResult.success?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                }

                changePasswordResult.error?.let {
                    //TODO: handle error later
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }

            })


        updateButton.setOnClickListener {
            loading.visibility = View.VISIBLE
            updateButton.isEnabled = false
            viewModel.changePassword(
                oldPassword = passwordEditText.text.toString(),
                newPassword = newPasswordEditText.text.toString(),
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}