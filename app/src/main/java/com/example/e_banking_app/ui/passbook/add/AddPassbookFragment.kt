package com.example.e_banking_app.ui.passbook.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_banking_app.data.model.input.PassbookInput
import com.example.e_banking_app.data.repository.PassbookRepository
import com.example.e_banking_app.databinding.FragmentAddPassbookBinding

class AddPassbookFragment : Fragment() {

    private lateinit var viewModel: AddPassbookViewModel
    private val args: AddPassbookFragmentArgs by navArgs()

    private var _binding: FragmentAddPassbookBinding? = null
    private val binding get() = _binding!!
    private val name get() = binding.passbookName
    private val interest get() = binding.passbookInterest
    private val period get() = binding.passbookPeriod
    private val amountEdt get() = binding.amount
    private val loading get() = binding.loading
    private val addBtn get() = binding.add


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPassbookBinding.inflate(inflater, container, false)
        name.text = args.passbookCategory.name_passbook
        interest.text = args.passbookCategory.interest_rate
        period.text = args.passbookCategory.period

        viewModel = AddPassbookViewModel(passbookRepository = PassbookRepository(context!!))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amountEdt.addTextChangedListener(afterTextChangedListener)

        viewModel.isValid.observe(viewLifecycleOwner, Observer { isValid ->
            isValid ?: return@Observer
            addBtn.isEnabled = isValid
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            state ?: return@Observer

            addBtn.isEnabled = true
            loading.visibility = View.GONE

            state.success?.let {
                val action =
                    AddPassbookFragmentDirections.actionAddPassbookFragmentPopIncludingPassbookFragment()
                findNavController().navigate(action)
            }

            state.error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        })

        addBtn.setOnClickListener {
            addBtn.isEnabled = false
            loading.visibility = View.VISIBLE
            viewModel.submit(
                PassbookInput(
                    money = Integer.parseInt(amountEdt.text.toString()),
                    id_category_passbook = args.passbookCategory.id_category_passbook
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
            viewModel.onChanged(s.toString())
        }
    }

}