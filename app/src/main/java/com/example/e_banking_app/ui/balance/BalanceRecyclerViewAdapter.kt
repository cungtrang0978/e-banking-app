package com.example.e_banking_app.ui.balance

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_banking_app.data.model.transaction.Transaction
import com.example.e_banking_app.databinding.FragmentBalanceItemBinding
import com.example.e_banking_app.ui.balance.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class BalanceRecyclerViewAdapter(
    private val values: List<Transaction>
) : RecyclerView.Adapter<BalanceRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentBalanceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id_tranfer
        holder.contentView.text = item.message
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentBalanceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}