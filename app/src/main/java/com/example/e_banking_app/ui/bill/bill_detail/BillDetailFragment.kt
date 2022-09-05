package com.example.e_banking_app.ui.bill.bill_detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.e_banking_app.R
import com.example.e_banking_app.data.factory.TransactionViewModelFactory
import com.example.e_banking_app.data.model.bill.getIconResource
import com.example.e_banking_app.databinding.FragmentBillDetailBinding

class BillDetailFragment : Fragment() {

    companion object {
        fun newInstance() = BillDetailFragment()
    }

    private var _binding: FragmentBillDetailBinding? = null
    private val binding get() = _binding!!

    private val loading get() = binding.loading
    private val payBtn get() = binding.payBtn


    private lateinit var viewModel: BillDetailViewModel

    private val args: BillDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            TransactionViewModelFactory(context!!)
        )[BillDetailViewModel::class.java]

        _binding = FragmentBillDetailBinding.inflate(inflater, container, false)

        binding.billAmount.text = args.billItem.money
        binding.billName.text = args.billItem.name_bill
        binding.billUnit.text = args.billItem.name_3rd
        binding.createdAt.text = args.billItem.created_at
        binding.billStatus.text = getString(args.billItem.statusValue)
        binding.icon.setImageResource(args.billItem.billType.getIconResource())

        val color = if (args.billItem.statusValue == R.string.unpaid) "#eb4034"
        else "#059913"
        binding.billStatus.setTextColor(Color.parseColor(color))

        binding.payBtn.visibility =
            if (args.billItem.statusValue == R.string.unpaid) View.VISIBLE
            else View.GONE
        payBtn.setOnClickListener {
            loading.visibility = View.VISIBLE
            payBtn.isEnabled = false
            viewModel.pay(args.billItem)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.billDetailResult.observe(viewLifecycleOwner) { result ->
            result ?: return@observe

            loading.visibility = View.GONE
            payBtn.isEnabled = true

            result.success?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
            result.error?.let {
                findNavController().popBackStack()
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}