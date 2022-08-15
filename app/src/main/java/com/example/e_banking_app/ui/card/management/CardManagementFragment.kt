package com.example.e_banking_app.ui.card.management

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
import com.example.e_banking_app.data.data_source.CardDataSource
import com.example.e_banking_app.data.repository.CardRepository
import com.example.e_banking_app.databinding.FragmentCardManagementBinding
import com.example.e_banking_app.utils.AuthUtils

/**
 * A fragment representing a list of Items.
 */
class CardManagementFragment : Fragment() {

    private lateinit var viewModel: CardManagementViewModel
    private var columnCount = 1

    private var _binding: FragmentCardManagementBinding? = null
    private val binding get() = _binding!!

    private val addCardBtn get() = binding.floatingActionButton
    private val rcvCard get() = binding.rcvCard
    private val loading get() = binding.loading
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//        }
//
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCardManagementBinding.inflate(inflater, container, false)
        // Set the adapter
        rcvCard.apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            CardManagementViewModel(cardRepository = CardRepository(cardDataSource = CardDataSource()))

        init()

        viewModel.cardManagementResult.observe(viewLifecycleOwner,
            Observer { result ->
                result ?: return@Observer
                loading.visibility = View.GONE
                result.success?.let {
                    rcvCard.adapter = CardManagementRecyclerViewAdapter(it)
                }
                result.error?.let {
                    Toast.makeText(context?.applicationContext, it, Toast.LENGTH_LONG).show()
                }

            })

        addCardBtn.setOnClickListener {
            val action =
                CardManagementFragmentDirections.actionCardManagementFragmentToAddCardFragment()
            findNavController().navigate(action)
        }
    }

    private fun init() {
        loading.visibility = View.VISIBLE
        viewModel.fetchCardList(AuthUtils.getToken(context))
    }


}