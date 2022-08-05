package com.example.e_banking_app.ui.register

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_banking_app.data.model.Gender
import com.example.e_banking_app.databinding.FragmentRegisterBinding
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : Fragment(), AdapterView.OnItemSelectedListener {


    private lateinit var registerViewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val args: RegisterFragmentArgs by navArgs()

    private val radioGroup get() = binding.rdGroup
    private val radioMale get() = binding.rdMale
    val radioFemale get() = binding.rdFemale
    val edtFullName get() = binding.fullName
    val edtDob get() = binding.dob
    val edtAddress get() = binding.address
    val edtEmail get() = binding.email
    val edtIdentity get() = binding.identityNumber
    private val btnRegister get() = binding.register
    private val branchSpinner get() = binding.branchSpinner
    private val progressLoading get() = binding.loading
    private var branch_id: Int? = null

    var myCalendar: Calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerViewModel =
            ViewModelProvider(this, RegisterViewModelFactory())[RegisterViewModel::class.java]

        init()

        radioGroup.setOnCheckedChangeListener { _, _ ->
            radioMale.isChecked
        }

        edtDob.setOnClickListener {
            showDatePicker()
        }

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                registerViewModel.dataChanged(
                    fullName = edtFullName.text.toString(),
                    dob = edtDob.text.toString(),
                    address = edtAddress.text.toString(),
                    email = edtEmail.text.toString(),
                    identity = edtIdentity.text.toString(),
                )
            }
        }

        edtFullName.addTextChangedListener(afterTextChangedListener)
        edtDob.addTextChangedListener(afterTextChangedListener)
        edtAddress.addTextChangedListener(afterTextChangedListener)
        edtEmail.addTextChangedListener(afterTextChangedListener)
        edtIdentity.addTextChangedListener(afterTextChangedListener)

        registerViewModel.registerFormState.observe(
            viewLifecycleOwner,
            Observer { registerFormState ->
                registerFormState ?: return@Observer
                btnRegister.isEnabled = registerFormState.isDataValid
                registerFormState.fullNameError?.let {
                    edtFullName.error = getString(it)
                }
                registerFormState.dobError?.let {
                    edtDob.error = getString(it)
                }
                registerFormState.addressError?.let {
                    edtAddress.error = getString(it)
                }
                registerFormState.emailError?.let {
                    edtEmail.error = getString(it)
                }
                registerFormState.identityError?.let {
                    edtIdentity.error = getString(it)
                }

            })

        registerViewModel.registerResult.observe(
            viewLifecycleOwner,
            Observer { registerResult ->
                registerResult ?: return@Observer
                progressLoading.visibility = View.GONE
                registerResult.success?.let {
                    val action =
                        RegisterFragmentDirections.actionRegisterFragmentPopToLoginFragment()
                    findNavController()
                        .navigate(action)
                }
                registerResult.error?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }

            }
        )

        btnRegister.setOnClickListener {
            val registerInput = args.registerInput.copy(
                fullName = edtFullName.text.toString(),
                dob = edtDob.text.toString(),
                address = edtAddress.text.toString(),
                email = edtEmail.text.toString(),
                gender = getGender(),
                citizen_identity_card = edtIdentity.text.toString(),
                id_branch = branch_id ?: 0,
            )
            progressLoading.visibility = View.VISIBLE
            registerViewModel.register(registerInput)
        }
    }

    private fun showDatePicker() {
        // Use the current date as the default date in the picker

        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        context?.let {
            DatePickerDialog(
                it,
                { _, year, monthOfYear, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, monthOfYear)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateLabel()
                },
                year,
                month,
                day
            ).show()
        }
    }

    private fun getGender(): Gender =
        if (radioMale.isChecked)
            Gender.male
        else
            Gender.female

    private fun updateLabel() {
        val myFormat = "yyyy/MM/dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        edtDob.setText(dateFormat.format(myCalendar.time))
    }

    private fun init() {
        progressLoading.visibility = View.VISIBLE
        registerViewModel.registerBranchResult.observe(
            viewLifecycleOwner,
            Observer { registerBranchResult ->
                registerBranchResult ?: return@Observer
                progressLoading.visibility = View.GONE
                registerBranchResult.success?.let { branchList ->
                    context?.let {
                        if (branchList.isNotEmpty()) {
                            branch_id = branchList[0].branchId
                        }

                        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            it,
                            R.layout.simple_spinner_item,
                            branchList.map { branch -> branch.nameBranch }
                        )
                        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                        branchSpinner.adapter = spinnerArrayAdapter

                    }
                }
                registerBranchResult.error?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                }
            }
        )

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        registerViewModel.registerBranchResult.value?.let {
            it.success?.let { list ->
                branch_id = list[pos].branchId
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}