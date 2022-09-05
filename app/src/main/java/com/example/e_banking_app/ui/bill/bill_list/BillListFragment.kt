package com.example.e_banking_app.ui.bill.bill_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_banking_app.data.factory.TransactionViewModelFactory
import com.example.e_banking_app.databinding.FragmentBillListBinding

/**
 * A fragment representing a list of Items.
 */
class BillListFragment : Fragment() {

    private var columnCount = 1

    private lateinit var viewModel: BillListViewModel
    private var _binding: FragmentBillListBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBillListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            TransactionViewModelFactory(context!!)
        )[BillListViewModel::class.java]

        binding.rcvList.apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }
        binding.loading.visibility = View.VISIBLE

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.billListResult.observe(viewLifecycleOwner) { result ->
            result ?: return@observe
            binding.loading.visibility = View.GONE

            result.success?.let {
                context?.let { context ->
                    binding.rcvList.adapter =
                        BillItemRecyclerViewAdapter(it, context) {
                            val action =
                                BillListFragmentDirections.actionBillListFragmentToBillDetailFragment(
                                    it
                                )
                            findNavController().navigate(action)
                        }
                }

            }
            result.error?.let {
                Toast.makeText(context?.applicationContext, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            BillListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}