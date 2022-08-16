package com.example.e_banking_app.ui.passbook.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_banking_app.data.repository.PassbookRepository
import com.example.e_banking_app.databinding.FragmentPassbookCategoryBinding

/**
 * A fragment representing a list of Items.
 */
class PassbookCategoryFragment : Fragment() {
    private var columnCount = 1
    private lateinit var viewModel: PassbookCategoryViewModel

    private var _binding: FragmentPassbookCategoryBinding? = null
    private val binding get() = _binding!!
    private val rcvCategory get() = binding.rcvPassbookCategory
    private val loading get() = binding.loading


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = PassbookCategoryViewModel(passbookRepository = PassbookRepository(context!!))
        _binding = FragmentPassbookCategoryBinding.inflate(inflater, container, false)
        rcvCategory.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        viewModel.passbookCategoryListState.observe(viewLifecycleOwner,
            Observer { state ->
                state ?: return@Observer
                loading.visibility = View.GONE
                state.success?.let {
                    rcvCategory.adapter = PassbookCategoryRecyclerViewAdapter(it)
                }

                state.error?.let {
                    Toast.makeText(context?.applicationContext, it, Toast.LENGTH_LONG).show()
                }


            })


    }

    private fun init() {
        loading.visibility = View.VISIBLE
        viewModel.fetchPassbookCategoryList()
    }


}