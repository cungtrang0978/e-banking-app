package com.example.e_banking_app.ui.passbook.management

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_banking_app.data.model.passbook.Passbook
import com.example.e_banking_app.databinding.FragmentPassbookItemBinding
import com.example.e_banking_app.utils.CurrencyUtils

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PassbookRecyclerViewAdapter(
    private val values: List<Passbook>,
    private val onWithdrawClick: ((Passbook) -> Unit)? = null,
) : RecyclerView.Adapter<PassbookRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentPassbookItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.id.text = item.id_passbook
        holder.name.text = item.name_passbook
        holder.interest.text = item.interest_rate+"%"
        holder.amount.text = CurrencyUtils.format(item.money)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentPassbookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val id: TextView = binding.id
        val name: TextView = binding.passbookName
        val interest: TextView = binding.passbookInterest
        val amount: TextView = binding.amount
        val withdrawBtn: Button = binding.withdrawBtn

        init {
            withdrawBtn.setOnClickListener {
                onWithdrawClick?.invoke(values[absoluteAdapterPosition])
            }
        }
    }

}