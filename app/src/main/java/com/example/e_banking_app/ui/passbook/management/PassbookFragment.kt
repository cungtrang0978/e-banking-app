package com.example.e_banking_app.ui.passbook.management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_banking_app.data.repository.PassbookRepository
import com.example.e_banking_app.databinding.FragmentPassbookBinding

/**
 * A fragment representing a list of Items.
 */
class PassbookFragment : Fragment() {

    private var columnCount = 1

    private lateinit var viewModel: PassbookViewModel
    private var _binding: FragmentPassbookBinding? = null
    private val binding get() = _binding!!
    private val loading get() = binding.loading
    private val rcvPassbook get() = binding.rcvPassbook
    private val addPassBookBtn get() = binding.floatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassbookBinding.inflate(inflater, container, false)

        // Set the adapter
        rcvPassbook.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = PassbookViewModel(PassbookRepository(context!!))
        init()

        viewModel.passbookResult.observe(
            viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer
                loading.visibility = View.GONE
                result.success?.let {
                    rcvPassbook.adapter = PassbookRecyclerViewAdapter(it)
                }
                result.error?.let {
                    Toast.makeText(context?.applicationContext, it, Toast.LENGTH_LONG).show()
                }

            }
        )

        addPassBookBtn.setOnClickListener {
            val action =
                PassbookFragmentDirections.actionPassbookFragmentToPassbookCategoryFragment()
            findNavController().navigate(action)
        }
    }

    private fun init() {
        loading.visibility = View.VISIBLE
        viewModel.fetchPassbookList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}