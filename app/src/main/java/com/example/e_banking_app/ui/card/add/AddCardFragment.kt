package com.example.e_banking_app.ui.card.add

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
import com.example.e_banking_app.data.model.input.CreateCardInput
import com.example.e_banking_app.databinding.FragmentAddCardBinding
import com.example.e_banking_app.utils.AuthUtils

class AddCardFragment : Fragment() {

    private lateinit var viewModel: AddCardViewModel
    private var _binding: FragmentAddCardBinding? = null


    private val binding get() = _binding!!

    private val pinEdt get() = binding.pin
    private val addBtn get() = binding.add
    private val loading get() = binding.loading

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, AddCardViewModelFactory())[AddCardViewModel::class.java]

        viewModel.addCardFormState.observe(viewLifecycleOwner,
            Observer { formState ->
                formState ?: return@Observer
                addBtn.isEnabled = formState.isDataValid
                formState.pinError?.let {
                    pinEdt.error = getString(it)
                }
            })

        viewModel.addCardResult.observe(viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer
                addBtn.isEnabled = true
                loading.visibility = View.GONE
                val appContext = context?.applicationContext

                result.success?.let {
                    Toast.makeText(appContext, it, Toast.LENGTH_LONG).show()
                }
                result.error?.let {
                    Toast.makeText(appContext, it, Toast.LENGTH_LONG).show()
                }

            })

        pinEdt.addTextChangedListener(afterTextChangedListener)

        addBtn.setOnClickListener {
            loading.visibility = View.VISIBLE
            addBtn.isEnabled = false
            viewModel.createCard(
                CreateCardInput(
                    pin = pinEdt.text.toString(),
                    token = AuthUtils.getToken(context)
                )
            )
        }
    }

    private val afterTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // ignore
        }

        override fun afterTextChanged(s: Editable) {
            viewModel.dataChanged(pinEdt.text.toString())

        }
    }
}